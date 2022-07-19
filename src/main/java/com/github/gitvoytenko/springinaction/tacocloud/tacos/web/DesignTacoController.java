package com.github.gitvoytenko.springinaction.tacocloud.tacos.web;

import com.github.gitvoytenko.springinaction.tacocloud.tacos.Ingredient;
import com.github.gitvoytenko.springinaction.tacocloud.tacos.Ingredient.Type;
import com.github.gitvoytenko.springinaction.tacocloud.tacos.Taco;
import com.github.gitvoytenko.springinaction.tacocloud.tacos.TacoOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    @ModelAttribute
    public void addIngredientsToModel(Model model){
        List<Ingredient> ingredients = Arrays.asList(
          new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
          new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
          new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
          new Ingredient("CARN", "Carnitas", Type.PROTEIN),
          new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
          new Ingredient("LETC", "Lettuce", Type.VEGGIES),
          new Ingredient("CHED", "Cheddar", Type.CHEESE),
          new Ingredient("JACK", "Monterrey JACK", Type.CHEESE),
          new Ingredient("SLSA", "Salsa", Type.SAUCE),
          new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );

        Type[] types = Ingredient.Type.values();
        for (Type type: types) {
            model.addAttribute(type.toString().toLowerCase(),
                    fillterByType(ingredients, type));
        }
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order(){
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco(){
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(){
        return "design";
    }

    @PostMapping
    public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {

        if(errors.hasErrors()) {
            return "design";
        }

        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);

        return "redirect:/orders/current";
    }

    private Iterable<Ingredient> fillterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
