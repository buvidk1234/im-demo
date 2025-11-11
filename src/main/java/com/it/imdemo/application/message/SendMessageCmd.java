package com.it.imdemo.application.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SendMessageCmd {

    @Schema(description="0=single,1=group")
    private Integer type;
    @Schema(description="")
    private Long senderId;
    @Schema(description="仅单聊使用")
    private Long targetId;
    @Schema(description="0=text,1=image,2=file,3=system")
    private Integer messageType;
    @Schema(description="")
    private String content;

}
