package com.fl.web.entity.shop;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TDesk extends BaseEntity {
    private String deskCode;
    private String deskName;
    private Integer sortnum;
}
