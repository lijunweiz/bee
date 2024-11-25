package cn.unminded.bee.manage.dto.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lijunwei
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ModelTreeDataResponse {

    @JsonProperty("id")
    private Long modelId;

    @JsonProperty("label")
    private String label;

    @JsonProperty("isLeaf")
    private Integer isLeaf;

    private String modelDesc;

    @JsonProperty("children")
    private List<ModelTreeDataResponse> treeData;
}
