package com.songspasssta.memberservice.infrastructure;

import com.songspasssta.common.exception.BadRequestException;
import com.songspasssta.memberservice.config.TokenExtractor;
import com.songspasssta.memberservice.config.TokenProvider;
import com.songspasssta.memberservice.domain.Accessor;
import com.songspasssta.memberservice.domain.Auth;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.songspasssta.common.exception.ExceptionCode.INVALID_REQUEST;

@RequiredArgsConstructor
@Component
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private final TokenProvider tokenProvider;
    private final TokenExtractor tokenExtractor;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.withContainingClass(Long.class)
                .hasParameterAnnotation(Auth.class);
    }

    @Override
    public Accessor resolveArgument(
            final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory
    ) {
        final HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        if (request == null) {
            throw new BadRequestException(INVALID_REQUEST);
        }

        final String accessToken = tokenExtractor.getAccessToken();
        final Long memberId = Long.valueOf(tokenProvider.getSubject(accessToken));

        return Accessor.member(memberId);
    }
}
