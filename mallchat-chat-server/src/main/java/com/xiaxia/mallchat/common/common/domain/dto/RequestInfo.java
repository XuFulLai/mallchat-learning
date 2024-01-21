package com.xiaxia.mallchat.common.common.domain.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author xfl
 * @date 2024/1/21 17:58
 */

@Data
@Builder
public class RequestInfo {
    private Long uid;

    private String ip;
}
