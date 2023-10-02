package cn.neorae.wtu.module.redirect;

import cn.neorae.common.annotation.FreePass;
import cn.neorae.wtu.common.exception.RedirectException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redirect")
@Tag(name = "重定向接口")
public class RedirectController {


    @GetMapping("/login")
    @Operation(summary = "重定向至登录")
    @FreePass
    public void unauthorized(String route) throws RedirectException {
           throw new RedirectException(route);
    }
}
