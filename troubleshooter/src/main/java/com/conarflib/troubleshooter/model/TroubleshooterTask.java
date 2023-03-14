package com.conarflib.troubleshooter.model;

import com.conarflib.util.ObjectUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TroubleshooterTask {

    private TaskStatus status;
    private String message = "";

    public TroubleshooterTask(TaskStatus status, String message) {
        this.setStatus(status);
        this.setMessage(message);
    }

    public void setStatus(TaskStatus status) {
        ObjectUtils.requiredNonNull(status, "Attribute [ status ] must not be null!");
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
