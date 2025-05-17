package cn.unminded.bee.manage.dto.rule.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lijunwei
 */
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Data
public class RuleSetTreeDataResponse {
    @JsonProperty("id")
    private Long ruleSetId;

    @JsonProperty("label")
    private String label;

    @JsonProperty("children")
    private List<RuleSetTreeDataResponse> treeData;
}
