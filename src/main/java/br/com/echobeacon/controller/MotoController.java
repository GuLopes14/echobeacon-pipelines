package br.com.echobeacon.controller;

import br.com.echobeacon.auth.AuthUtils;
import br.com.echobeacon.model.Moto;
import br.com.echobeacon.service.MotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/motos")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @GetMapping
    public String listarMotos(Model model, Authentication authentication) {
        List<Moto> todasMotos = motoService.listarTodos();
        List<Moto> motosRecepcao = todasMotos.stream()
            .filter(m -> m.getEchoBeacon() == null)
            .toList();
        List<Moto> motosPatio = todasMotos.stream()
            .filter(m -> m.getEchoBeacon() != null)
            .toList();

        model.addAttribute("motosRecepcao", motosRecepcao);
        model.addAttribute("motosPatio", motosPatio);

        // Verificar se o usuário atual é administrador
        boolean isAdmin = AuthUtils.isAdmin(authentication);
        model.addAttribute("isAdmin", isAdmin);

        return "motos";
    }

    @GetMapping("/novo")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("moto", new Moto());
        return "cadastro-moto";
    }

    @PostMapping
    public String salvarMoto(@Valid @ModelAttribute Moto moto, org.springframework.validation.BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("moto", moto);
            return "cadastro-moto";
        }
        motoService.salvar(moto);
        return "redirect:/motos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        Moto moto = motoService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Moto inválida com id: " + id));
        model.addAttribute("moto", moto);
        return "editar-moto";
    }

    @PostMapping("/editar/{id}")
    public String atualizarMoto(@PathVariable Long id, @ModelAttribute Moto moto) {
        Moto motoOriginal = motoService.buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Moto inválida com id: " + id));
        moto.setEchoBeacon(motoOriginal.getEchoBeacon());
        motoService.salvar(moto);
        return "redirect:/motos";
    }

    @GetMapping("/excluir/{id}")
    public String excluirMoto(@PathVariable Long id) {
        motoService.excluir(id);
        return "redirect:/motos";
    }
}
