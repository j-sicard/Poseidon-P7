package com.nnk.springboot.controlleurtest;

import com.nnk.springboot.AbstractConfigurationTest;
import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;;
import com.nnk.springboot.service.RuleNameService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

public class RuleNameControllerTests extends AbstractConfigurationTest {
    @InjectMocks
    private RuleNameController ruleNameController;

    @Mock
    private RuleNameService ruleNameService;

    @Mock
    private Model model;

    @Test
    public void homeTest() {
        RuleName ruleName = new RuleName();
        ruleName.setName("nameTest");
        ruleName.setDescription("DescritpionTest");
        ruleName.setJson("JsonTest");
        ruleName.setTemplate("TemplateTest");
        ruleName.setSqlStr("SqlStrTest");
        ruleName.setSqlPart("SqlPartTest");

        List<RuleName> ruleNames = new ArrayList<>();
        ruleNames.add(ruleName);

        when(ruleNameService.getAllRuleNames()).thenReturn(ruleNames);
        String viewName = ruleNameController.home(model);

        assertEquals("ruleName/list", viewName);
        verify(model).addAttribute("ruleName", ruleNames);
    }

    @Test
    public void addRuleFormTest() {
        String viewName = ruleNameController.addRuleForm(model);

        assertEquals("ruleName/add", viewName);

        verify(model).addAttribute(eq("ruleName"), Mockito.any(RuleName.class));
    }

    @Test
    public void validateRuleNameWithNoErrorsTest() {
        // Objet RuleName valide
        RuleName ruleName = new RuleName();
        ruleName.setName("NameTest");

        // Simuler un BindingResult sans erreurs
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);

        // Appeler la méthode validate
        String viewName = ruleNameController.validate(ruleName, result, model);

        verify(ruleNameService, times(1)).saveRuleName(ruleName);

        assertEquals("redirect:/ruleName/list", viewName);
    }

    @Test
    public void validateRuleNameWithErrorsTest() {
        // Objet RuleName invalide
        RuleName ruleName = new RuleName();
        ruleName.setName("10");

        // Simuler un BindingResult avec des erreurs
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        String viewName = ruleNameController.validate(ruleName, result, model);

        verify(ruleNameService, never()).saveRuleName(ruleName);

        // Vérifier que la vue renvoyée est la vue d'ajout (car il y a des erreurs)
        assertEquals("ruleName/add", viewName);
    }

    @Test
    public void showUpdateFormtest() {
        Integer ruleNameId = 1;
        RuleName expectedRuleName = new RuleName();
        expectedRuleName.setId(ruleNameId);

        // retourne l'enchère simulée lorsque getbyid est appelé
        when(ruleNameService.getById(ruleNameId)).thenReturn(Optional.of(expectedRuleName));

        // Appelez la méthode showUpdateForm
        String viewName = ruleNameController.showUpdateForm(ruleNameId, model);

        // Vérifie que l'objet RuleName a été ajouté au modèle avec le nom "ruleName"
        verify(model).addAttribute("ruleName", expectedRuleName);

        // Vérifie que le nom de la vue retournée est "ruleName/update"
        assertEquals("ruleName/update", viewName);
    }

    @Test
    public void updateRuleNameSuccessTest() {
        Integer ruleNameId = 1;
        RuleName ruleName = new RuleName();
        ruleName.setId(ruleNameId);

        // Configure le service pour retourner true lorsqu'on vérifie la présence de l'enchère
        when(ruleNameService.getById(ruleNameId)).thenReturn(Optional.of(ruleName));

        // Appele la méthode updateRuleName et un objet RuleName valide
        String viewName = ruleNameController.updateRuleName(ruleNameId, ruleName, mock(BindingResult.class), model);

        // Vérifie que l'enchère est enregistrée avec le service
        verify(ruleNameService).saveRuleName(ruleName);

        // Vérifie que le nom de la vue retournée est "redirect:/ruleName/list"
        assertEquals("redirect:/ruleName/list", viewName);
    }

    @Test
    public void updateRuleNameInvalidIdTest() {
        Integer ruleNameId = 999;

        // Configure le service pour retourner false lorsqu'on vérifie la présence de l'enchère
        when(ruleNameService.getById(ruleNameId)).thenReturn(Optional.empty());

        // Appele la méthode updateRuleName et un objet RuleName valide
        String viewName = ruleNameController.updateRuleName(ruleNameId, new RuleName(), mock(BindingResult.class), model);

        // Vérifie que la méthode redirige vers "/ruleName/list"
        assertEquals("redirect:/ruleName/list", viewName);

        // Vérifie que la méthode n'essaie pas de sauvegarder l'enchère
        verify(ruleNameService, never()).saveRuleName(any(RuleName.class));
    }

    @Test
    public void updateRuleNameValidationProblemTest() {
        Integer ruleNameId = 1;
        RuleName ruleName = new RuleName();
        ruleName.setId(ruleNameId);

        // Configure le service pour retourner true lorsqu'on vérifie la présence de l'enchère
        when(ruleNameService.getById(ruleNameId)).thenReturn(Optional.of(ruleName));

        // Configure le BindingResult pour indiquer qu'il y a des erreurs de validation
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        // Appel la méthode updateRuleName et un objet RuleName avec des erreurs de validation
        String viewName = ruleNameController.updateRuleName(ruleNameId, ruleName, bindingResult, model);

        // Vérifie que la méthode renvoie la vue "/ruleName/add"
        assertEquals("ruleName/add", viewName);

        // Vérifie que la méthode n'essaie pas de sauvegarder l'enchère
        verify(ruleNameService, never()).saveRuleName(any(RuleName.class));
    }

    @Test
    public void deleteRuleNameTest() {
        Integer ruleNameId = 1;
        RuleName ruleName = new RuleName();
        ruleName.setId(ruleNameId);

        // Configure le service pour retourner l'enchère lorsqu'on vérifie la présence de l'enchère
        when(ruleNameService.getById(ruleNameId)).thenReturn(Optional.of(ruleName));

        // Appele la méthode deleteBid
        String viewName = ruleNameController.deleteRuleName(ruleNameId, model);

        // Vérifie que l'enchère est supprimée avec le service
        verify(ruleNameService).deleteRuleName(ruleName);

        // Vérifie que le nom de la vue retournée est "redirect:/ruleName/list"
        assertEquals("redirect:/ruleName/list", viewName);

        // Vérifie que la liste des enchères est ajoutée au modèle
        verify(model).addAttribute(eq("ruleName"), anyList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteCurveInvalidId() {
        Integer ruleNameId = 999;

        // Configure le service pour retourner false lorsqu'on vérifie la présence de l'enchère
        when(ruleNameService.getById(ruleNameId)).thenReturn(Optional.empty());

        // Appele la méthode deleteBid avec l'ID factice
        ruleNameController.deleteRuleName(ruleNameId, model);
    }
}
