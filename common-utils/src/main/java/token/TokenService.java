package token;

import exception.AnnotationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Component
public class TokenService {

    @Autowired
    private RedisService redisService;

    public String createToken() {
        String uuid = UUID.randomUUID().toString();
        redisService.setEx(uuid, uuid, 1000L);

        return uuid;
    }

    public void checkToken(HttpServletRequest req) throws AnnotationException {

        String token = req.getHeader("token");// getHeader()是获取HTTP头部信息,getParameter()是获取表单参数
        if (StringUtils.isEmpty(token)) {
            token = req.getParameter("token");
            if (StringUtils.isEmpty(token)) {
                throw new AnnotationException("token不存在");
            }
        }

        /*if (!redisService.exists(token)) {
            throw new AnnotationException("重复的操作");
        }

        boolean remove = redisService.remove(token);
        if (!remove) {
            throw new AnnotationException("重复的操作");
        }*/
    }

    public void checkToken_1(boolean isCheck) throws AnnotationException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest req = attributes.getRequest();
        String token = req.getHeader("token");// getHeader()是获取HTTP头部信息,getParameter()是获取表单参数
        if (StringUtils.isEmpty(token)) {
            token = req.getParameter("token");
            if (StringUtils.isEmpty(token)) {
                if (isCheck) {
                    throw new AnnotationException("token不存在");
                }
            }
        }


    }
}
