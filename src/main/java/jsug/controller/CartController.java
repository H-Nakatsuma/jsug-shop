package jsug.controller;

import jsug.controller.form.CartForm;
import jsug.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by uu083549 on 2016/10/21.
 */

@Controller
@RequestMapping("cart")
public class CartController {

    @Autowired
    Cart cart;

    @RequestMapping(method = RequestMethod.GET)
    String viewCart(Model model) {
        model.addAttribute("orderLines", cart.getOrderLines());
        return "cart/viewCart";
    }

    @RequestMapping(method = RequestMethod.POST)
    String delete(@Validated CartForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "商品がチェックされていません");
            return viewCart(model);
        }
        cart.remove(form.getLineNo());
        return "redirect:/cart";
    }

}
