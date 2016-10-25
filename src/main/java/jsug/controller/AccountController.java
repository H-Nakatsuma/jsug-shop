package jsug.controller;

import jsug.controller.form.AccountForm;
import jsug.model.Account;
import jsug.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by uu083549 on 2016/10/20.
 */
@Controller
@RequestMapping("account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @ModelAttribute
    AccountForm setupForm() {
        return new AccountForm();
    }

    @RequestMapping(path = "create", params = "form", method=RequestMethod.GET)
    String createForm() {
        return "account/createForm";
    }

    @RequestMapping(path = "create", method=RequestMethod.POST)
    String create(@Validated AccountForm accountForm, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "account/createForm";
        }
        Account account = Account.builder()
                .email(accountForm.getEmail())
                .name(accountForm.getName())
                .birthDay(accountForm.getBirthDay())
                .zip(accountForm.getZip())
                .address(accountForm.getAddress())
                .build();
        accountService.register(account, accountForm.getPassword());
        attributes.addFlashAttribute(account);
        return "redirect:/account/create?finish";
    }

    @RequestMapping(value = "create", params = "finish", method = RequestMethod.GET)
    String createFinish() {
        return "account/createFinish";
    }
}
