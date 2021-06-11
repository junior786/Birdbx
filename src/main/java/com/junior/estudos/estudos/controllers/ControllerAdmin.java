package com.junior.estudos.estudos.controllers;

import com.junior.estudos.estudos.entidade.Roletb;
import com.junior.estudos.estudos.entidade.Usuario;
import com.junior.estudos.estudos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class ControllerAdmin {
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/admin")
    public ModelAndView AdminGET() {
        Authentication credentials = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = userRepository.findByEmail(credentials.getName());

        ModelAndView modelAndView = new ModelAndView("/admin");
        List<Usuario> usuarios = userRepository.findAll();
        usuarios.remove(usuario);

        modelAndView.addObject("usuarios", usuarios);
        return modelAndView;
    }

    @RequestMapping("deletarUsuario{usuario.id}")
    public String deletarUsuario(@PathVariable("usuario.id") long id) {
        Usuario usuario = userRepository.findById(id).get();
        List<Roletb> roles = usuario.getRoles();
        roles.clear();
        usuario.setRoles(roles);
        userRepository.save(usuario);

        userRepository.delete(usuario);
        System.out.println("AEEEEEH");
        return "redirect:/admin";
    }
    @GetMapping("editarUsuario{usuario.id}")
    public ModelAndView editarUsuarioGET(@PathVariable("usuario.id") long id){
       ModelAndView mv = new ModelAndView("/editaruser");
        Usuario user = userRepository.findById(id).get();
        mv.addObject("usuario",user);
        return mv;
    }

    @PostMapping("editarUsuario{usuario.id}")
    public String editarUsuarioPOST(@Valid Usuario usuario, @PathVariable("usuario.id") long id){
      Usuario user = userRepository.findById(id).get();
      user.setNome(usuario.getNome());
      user.setEmail(usuario.getEmail());
      user.setNomeCriatorio(usuario.getNomeCriatorio());
      if (!user.getPassword().equals(usuario.getPassword())){
          user.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
      }
      userRepository.save(user);
      return "redirect:/admin";
    }

}
