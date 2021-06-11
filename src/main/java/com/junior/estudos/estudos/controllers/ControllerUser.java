package com.junior.estudos.estudos.controllers;

import com.junior.estudos.estudos.entidade.Gaiola;
import com.junior.estudos.estudos.entidade.Passaro;
import com.junior.estudos.estudos.entidade.Usuario;
import com.junior.estudos.estudos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControllerUser {
    @Autowired
    UserRepository userRepository;
    @GetMapping(value = "/static")
    public ModelAndView estatic() {
        Authentication credentials = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = userRepository.findByEmail(credentials.getName());
        int numeroFilhotes = 0;
        List<Gaiola> gaiolas = usuario.getGaiola();
        List<Passaro> passaros = usuario.getPassaro();
        int numerosGaiola = gaiolas.size();
        int numeroPassaros = passaros.size();
        for (Gaiola gaiola: gaiolas){
            if (gaiola.getNumeroFilhotes() != 0 ){
                numeroFilhotes += gaiola.getNumeroFilhotes();
            }
        }
        ModelAndView mv = new ModelAndView("static");

        mv.addObject("gaiolas",gaiolas);
        mv.addObject("passaros", passaros);
        mv.addObject("numeroGaiolas",numerosGaiola);
        mv.addObject("numeroPassaros",numeroPassaros);
        mv.addObject("numeroFilhotes",numeroFilhotes);
        return mv;
    }

    @GetMapping(value = "/logout")
    public String logout(){
        return "redirect:/login";
    }

}
