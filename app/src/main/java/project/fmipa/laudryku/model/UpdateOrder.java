package project.fmipa.laudryku.model;

import com.google.gson.annotations.SerializedName;

public class UpdateOrder {
    @SerializedName("status")
    private String Status;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}

