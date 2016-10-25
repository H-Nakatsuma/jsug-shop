package jsug.controller;

import jsug.model.Cart;
import jsug.model.Order;
import jsug.component.exception.EmptyCartOrderException;
import jsug.component.exception.InvalidCartOrderException;
import jsug.service.OrderService;
import jsug.model.ShopUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by uu083549 on 2016/10/24.
 */

@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    Cart cart;
    @Autowired
    OrderService orderService;

    @RequestMapping(method = RequestMethod.GET, params = "confirm")
    String confirm(@AuthenticationPrincipal ShopUserDetails userDetails, Model model) {
        model.addAttribute("orderLines", cart.getOrderLines());
        if (cart.isEmpty()) {
            model.addAttribute("error", "買い物カゴが空です");
            return "cart/viewCart";
        }
        model.addAttribute("account", userDetails.getAccount());
        model.addAttribute("signature", orderService.calcSignature(cart));
        return "order/confirm";
    }

    @RequestMapping(method = RequestMethod.POST)
    String order(@AuthenticationPrincipal ShopUserDetails userDetails, @RequestParam String signature, RedirectAttributes attributes) {
        Order order = orderService.purchase(userDetails.getAccount(), cart, signature);
        attributes.addFlashAttribute(order);
        return "redirect:/order?finish";
    }

    @RequestMapping(params = "finish", method = RequestMethod.GET)
    String finish() {
        return "order/finish";
    }

    @ExceptionHandler({EmptyCartOrderException.class, InvalidCartOrderException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    ModelAndView handleOrderException(RuntimeException e) {
        return new ModelAndView("order/error")
                .addObject("error", e.getMessage());
    }

}
