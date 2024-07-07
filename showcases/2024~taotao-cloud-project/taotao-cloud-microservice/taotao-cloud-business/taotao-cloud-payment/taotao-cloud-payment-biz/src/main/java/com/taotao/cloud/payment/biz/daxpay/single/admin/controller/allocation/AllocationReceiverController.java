package com.taotao.cloud.payment.biz.daxpay.single.admin.controller.allocation;

import cn.bootx.platform.common.core.rest.PageResult;
import cn.bootx.platform.common.core.rest.Res;
import cn.bootx.platform.common.core.rest.ResResult;
import cn.bootx.platform.common.core.rest.dto.LabelValue;
import cn.bootx.platform.common.core.rest.param.PageParam;
import cn.bootx.platform.daxpay.service.core.payment.allocation.service.AllocationReceiverService;
import cn.bootx.platform.daxpay.service.dto.allocation.AllocationReceiverDto;
import cn.bootx.platform.daxpay.service.param.allocation.group.AllocationReceiverParam;
import cn.bootx.platform.daxpay.service.param.allocation.group.AllocationReceiverQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分账接收方控制器
 * @author xxm
 * @since 2024/3/28
 */
@Tag(name = "分账接收方控制器")
@RestController
@RequestMapping("/allocation/receiver")
@RequiredArgsConstructor
public class AllocationReceiverController {

    private final AllocationReceiverService receiverService;

    @Operation(summary = "分页")
    @GetMapping("/page")
    public ResResult<PageResult<AllocationReceiverDto>> page(PageParam pageParam, AllocationReceiverQuery query){
        return Res.ok(receiverService.page(pageParam, query));
    }

    @Operation(summary = "查询详情")
    @GetMapping("/findById")
    public ResResult<AllocationReceiverDto> findById(Long id){
        return Res.ok(receiverService.findById(id));
    }

    @Operation(summary = "获取可以分账的通道")
    @GetMapping("/findChannels")
    public ResResult<List<LabelValue>> findChannels(){
        return Res.ok(receiverService.findChannels());
    }

    @Operation(summary = "根据通道获取分账接收方类型")
    @GetMapping("/findReceiverTypeByChannel")
    public ResResult<List<LabelValue>> findReceiverTypeByChannel(String channel){
        return Res.ok(receiverService.findReceiverTypeByChannel(channel));
    }

    @Operation(summary = "新增")
    @PostMapping("add")
    public ResResult<Void> add(@RequestBody AllocationReceiverParam param){
        receiverService.add(param);
        return Res.ok();
    }

    @Operation(summary = "修改")
    @PostMapping("update")
    public ResResult<Void> update(@RequestBody AllocationReceiverParam param){
        receiverService.update(param);
        return Res.ok();
    }

    @Operation(summary = "删除")
    @PostMapping("delete")
    public ResResult<Void> delete(Long id){
        receiverService.remove(id);
        return Res.ok();
    }

    @Operation(summary = "同步到三方支付系统中")
    @PostMapping("registerByGateway")
    public ResResult<Void> registerByGateway(Long id){
        receiverService.registerByGateway(id);
        return Res.ok();
    }

    @Operation(summary = "从三方支付系统中删除")
    @PostMapping("removeByGateway")
    public ResResult<Void> removeByGateway(Long id){
        receiverService.removeByGateway(id);
        return Res.ok();
    }

}
