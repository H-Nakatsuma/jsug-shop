package jsug.controller.form;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class AddToCartForm {
    @NotNull
    private UUID goodsId;
    @NotNull
    @Min(1)
    @Max(50)
    private Integer quantity;
    @NotNull
    private Integer categoryId;
}