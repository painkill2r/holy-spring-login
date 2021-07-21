package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 스프링 인터셉터 흐름
 * - HTTP 요청 -> WAS -> 필터 -> 서블릿(디스패처 서블릿) -> 스프링 인터셉터 -> 컨트롤러
 * <p>
 * 요청 로그 인터셉터
 */
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    /**
     * 컨트롤러 호출 전에 호출
     * 더 정확히는 핸들러 어댑터 호출 전데 호출
     *
     * @param request
     * @param response
     * @param handler
     * @return 응답 값이 true이면 다음으로 진행하고, false이면 더는 진행하지 않는다. false인 경우 나머지 인터셉터는 물론이고, 핸들러 어댑터도 호출되지 않는다.
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        request.setAttribute(LOG_ID, uuid); //UUID를 postHandle, afterCompletion에서 사용할 수 있게 request에 저장

        /**
         * @RequestMapping: HandlerMethod
         * 정적 리소스: ResourceHttpRequestHandler
         */
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;//호출할 컨트롤러 메소드의 모든 정보가 포함되어 있음.
        }

        log.info("REQUEST [{}] [{}] [{}]", uuid, requestURI, handler);

        return true;
    }

    /**
     * 컨트롤러 호출 후 호출
     * 더 정확히는 핸들러 어댑터 호출 후에 호출
     * 컨트롤러에서 예외가 발생하면 호출되지 않음.
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView);
    }

    /**
     * 뷰가 렌더링 된 이후 호출
     * 컨트롤러에서 예외가 발생하면 예외를 파라미터로 받아서 어떤 예외가 발생했는지 로그로 출력할 수도 있음.
     * 예외와 무관하게 공통 처리를 하려면 afterCompletion를 사용해야 한다.
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = (String) request.getAttribute(LOG_ID);

        log.info("RESPONSE [{}] [{}] [{}]", logId, requestURI, handler);

        if (ex != null) {
            log.error("afterCompletion error!!", ex);
        }
    }
}
