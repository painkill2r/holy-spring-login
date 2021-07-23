package hello.login.web.argumentresolver;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 로그인 아규먼트 리졸버
 */
@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 파라미터로 넘어온 인자가 @Login 어노테이션 정보를 가지고 있고, Member 타입인지 확인해서
     * 아규먼트 리졸버가 동작하게 한다.
     *
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParamter 실행");

        boolean hasLoginAnnotaion = parameter.hasParameterAnnotation(Login.class); //파라미터에 @Login 어노테이션 정보가 있는지 확인
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType()); //파라미터 타입이 Member 타입인지 확인

        return hasLoginAnnotaion && hasMemberType;
    }

    /**
     * 컨트롤러 호출 직전에 호출되어서 필요한 파라미터 정보를 생성해준다.
     * 여기서는 세션에 있는 로그인 회원 정보인 member 객체를 찾아서 반환해준다.
     * 이후 스프링 MVC 컨트롤러의 메소드를 호출하면서 여거에서 반환된 member 객체를 파라미터에 전달해준다.
     *
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("resolveArgument 실행");

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession();

        if (session == null) {
            return null;
        }

        return session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
