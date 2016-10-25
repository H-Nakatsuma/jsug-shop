package jsug.controller;

import jsug.component.exception.GoodsNotFoundException;
import jsug.controller.form.AddToCartForm;
import jsug.model.Cart;
import jsug.model.Category;
import jsug.model.Goods;
import jsug.model.OrderLine;
import jsug.service.CategoryService;
import jsug.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by uu083549 on 2016/10/20.
 */
@Controller
public class GoodsController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    Cart cart;

    @ModelAttribute("categories")
    List<Category> getCategories() {
        return categoryService.findAll();
    }

    @ModelAttribute
    AddToCartForm addToCartForm() {
        return new AddToCartForm();
    }

    @RequestMapping(path = "/")
    String showGoods(@RequestParam(defaultValue = "1") Integer categoryId, @PageableDefault Pageable pageable, Model model) {
        Page<Goods> page = goodsService.findByCategoryId(categoryId, pageable);
        model.addAttribute("page", page);
        model.addAttribute("categoryId", categoryId);
        return "goods/showGoods";
    }

    @RequestMapping(value = "/addToCart", method = RequestMethod.POST)
    String addToCart(@Validated AddToCartForm form, BindingResult result,
                     @PageableDefault Pageable pageable, Model model) {
        if (result.hasErrors()) {
            return showGoods(form.getCategoryId(), pageable, model);
        }
        Goods goods = goodsService.findOne(form.getGoodsId());
        cart.add(OrderLine.builder()
                .goods(goods)
                .quantity(form.getQuantity())
                .build());
        return "redirect:/cart";
    }

    @ExceptionHandler(GoodsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handleGoodsNotFoundException() {
        return "goods/notFound";
    }

}
