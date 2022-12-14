package wx.api.rest.account.tenant;

import static wx.api.rest.shared.dto.envelope.ResponseEnvelope.createOk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wx.api.rest.shared.BaseController;
import wx.api.rest.shared.dto.envelope.ResponseEnvelope;
import wx.application.account.user.UserDetail;

@RestController
@RequestMapping("/tenant")
@Api(tags = "π« η§ζ·ηΈε³ζ₯ε£")
public class TenantController extends BaseController {

  @GetMapping("/user")
  @ApiOperation(value = "θ·εε½εη¨ζ·ζε±η§ζ·δΈζζη¨ζ·")
  public ResponseEnvelope<List<UserDetail>> getAllUser() {
    return createOk(userQueryService.findUsers(getTenantId()).blockingGet());
  }
}
