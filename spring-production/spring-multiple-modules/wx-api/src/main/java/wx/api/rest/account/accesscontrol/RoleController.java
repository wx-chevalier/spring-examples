package wx.api.rest.account.accesscontrol;

import static wx.api.rest.shared.dto.envelope.ResponseEnvelope.createOk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
import wx.api.rest.shared.BaseController;
import wx.api.rest.shared.dto.envelope.ResponseEnvelope;
import wx.application.account.role.RoleDetail;
import wx.common.data.shared.id.*;
import wx.common.data.shared.id.RoleId;
import wx.common.data.shared.id.UserId;
import wx.domain.account.Permission;
import wx.domain.account.Role;

@RestController
@RequestMapping("/role")
@Api(tags = "π θ§θ²ηΈε³ζ₯ε£")
public class RoleController extends BaseController {

  @PostMapping
  @ApiOperation(value = " ζ°ε’ζζ΄ζ°θ§θ²")
  public ResponseEnvelope<Role> saveOrUpdateRole(@RequestBody RoleAddRequest req) {
    Role role = req.toRole();
    if (req.getTenantId() == null) {
      role.setTenantId(getTenantId());
    }
    role.setCreatorId(getCurrentUserId());
    return createOk(accessControlCommandService.addRole(role));
  }

  @GetMapping
  @ApiOperation(value = " θ·εε½εη§ζ·δΈηζζηθ§θ²")
  public ResponseEnvelope<List<RoleDetail>> getAllRoles() {
    return createOk(accessControlQueryService.findRoles(getTenantId()));
  }

  @PatchMapping
  @ApiOperation(value = "ζ΄ζ°θ§θ²δΏ‘ζ―")
  public ResponseEnvelope<Role> updateRole(RoleUpdateRequest req) {
    TenantId tenantId = getTenantId();
    Role role = req.toRole().setId(RoleId.create(req.getId())).setTenantId(tenantId);
    return createOk(accessControlCommandService.addRole(role));
  }

  @DeleteMapping("/{roleName}")
  @ApiOperation(value = "ε ι€θ§θ²")
  public ResponseEnvelope<Void> deleteRole(@PathVariable("roleName") String roleName) {
    accessControlCommandService.removeRole(getTenantId(), roleName);
    return createOk();
  }

  @PostMapping("/{roleName}/permission")
  @ApiOperation(value = " δΈΊζε?θ§θ²ζ·»ε ζι")
  public ResponseEnvelope<Void> updateRolePermissionRelation(
      @PathVariable("roleName") String roleName, @RequestBody @Valid Set<String> permissionNames) {
    TenantId tenantId = getTenantId();
    UserId currentUserId = getCurrentUserId();
    accessControlCommandService.addRolePermissions(
        tenantId,
        currentUserId,
        new Role(roleName, tenantId),
        permissionNames.stream()
            .map(name -> new Permission(tenantId, name))
            .collect(Collectors.toList()));
    return createOk();
  }

  @DeleteMapping("/{roleName}/permission")
  @ApiOperation(value = " δΈΊζε?θ§θ²η§»ι€ζι")
  public ResponseEnvelope<Void> removeRolePermissionRelation(
      @PathVariable("roleName") String roleName, @RequestBody @Valid Set<String> permissionNames) {
    TenantId tenantId = getTenantId();
    accessControlCommandService.removeRolePermissions(
        tenantId,
        new Role(roleName, tenantId),
        permissionNames.stream()
            .map(name -> new Permission(tenantId, name))
            .collect(Collectors.toList()));
    return createOk();
  }
}
