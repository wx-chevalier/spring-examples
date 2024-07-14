import com.fc.v2.model.custom.SysMenu;
import com.fc.v2.service.ITSysPermissionService;
import com.fc.v2.shiro.util.ShiroUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GetUserMenu {

    @Autowired
    public ITSysPermissionService sysPermissionService;

    @Test
    public void getMenu(){
        List<SysMenu> sysMenus = sysPermissionService.getSysMenus(ShiroUtils.getUserId());
        System.out.println(sysMenus.toArray());
    }
}
