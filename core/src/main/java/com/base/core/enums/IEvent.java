package com.base.core.enums;

import java.io.Serializable;

public interface IEvent extends Serializable {

    String getName();

    String getCode();
}

