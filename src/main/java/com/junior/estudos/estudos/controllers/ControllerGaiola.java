package com.junior.estudos.estudos.controllers;

import com.junior.estudos.estudos.entidade.Gaiola;
import com.junior.estudos.estudos.entidade.Passaro;
import com.junior.estudos.estudos.entidade.Roletb;
import com.junior.estudos.estudos.entidade.Usuario;
import com.junior.estudos.estudos.repository.GaiolaRepository;
import com.junior.estudos.estudos.repository.PassaroRepository;
import com.junior.estudos.estudos.repository.RoleRepository;
import com.junior.estudos.estudos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControllerGaiola {

    @Autowired
    GaiolaRepository gaiolaRepository;
    @Autowired
    PassaroRepository passaroRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(path = "/cadastro", method = RequestMethod.GET)
    public String cadastroGET() {
        return "/cadastro";
    }

    @RequestMapping(value = "/cadastro", method = RequestMethod.POST)
    public String cadastroPOST(@Valid Usuario usuario) {
        System.out.println("==========" + usuario.getNome());
        Roletb role = roleRepository.findById("ROLE_USER").get();
        List<Usuario> usuarios = userRepository.findAll();
        for (Usuario usuarioemail : usuarios) {
            if (usuarioemail.getEmail() != null) {
                if (usuarioemail.getEmail().equals(usuario.getEmail())) {
                    return "redirect:/cadastro";
                }
            }
        }
        List<Roletb> lista = new ArrayList<>();
        lista.add(role);
        usuario.setRoles(lista);
        usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
        userRepository.save(usuario);

        return "redirect:/login";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ModelAndView returnIndex() {
        Authentication credentials = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = userRepository.findByEmail(credentials.getName());
        ModelAndView mv = new ModelAndView("index");
        List<Gaiola> gaiolas = usuario.getGaiola();
        gaiolas.sort((first, second) -> (int) (first.getNumero() - second.getNumero()));
        mv.addObject("gaiolas", gaiolas);
        mv.addObject("usuario",usuario);
        return mv;
    }


    @RequestMapping(value = "/gaiola", method = RequestMethod.GET)
    public ModelAndView listaPassaros() {
        Authentication credentials = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = userRepository.findByEmail(credentials.getName());

        ModelAndView mv = new ModelAndView("gaiola");
        List<Gaiola> gaiolas = usuario.getGaiola();
        gaiolas.sort((first, second) -> (int) (first.getNumero() - second.getNumero()));
        mv.addObject("gaiolas", gaiolas);
        return mv;
    }

    @RequestMapping(value = "/gaiola", method = RequestMethod.POST)
    public String cadastrarGaiola(@Valid Gaiola gaiola) {
        Authentication credentials = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = userRepository.findByEmail(credentials.getName());

        List<Gaiola> gaiolas = usuario.getGaiola();
        for (Gaiola gaiolaId : gaiolas) {
            if (gaiolaId.getNumero() == gaiola.getNumero()) {
                return "redirect:/gaiola";
            }
        }
        gaiolas.add(gaiola);
        usuario.setGaiola(gaiolas);
        gaiola.setUsuario(usuario);
        gaiolaRepository.save(gaiola);
        userRepository.save(usuario);
        return "redirect:/";

    }

    @RequestMapping(value = "/passaros", method = RequestMethod.GET)
    public ModelAndView cadastrarPassaroGET() {
        Authentication credentials = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = userRepository.findByEmail(credentials.getName());


        ModelAndView mv = new ModelAndView();

        List<Passaro> passaros = usuario.getPassaro();

        List<Gaiola> gaiolas = usuario.getGaiola();
        mv.addObject("passaros", passaros);
        mv.addObject("gaiolas", gaiolas);
        return mv;
    }

    @RequestMapping(value = "/passaros", method = RequestMethod.POST)
    public String cadastrarPassaroPost(@Valid Passaro passaro, BindingResult result, RedirectAttributes attributes) {
        if (passaro.getRaca().equals("") || passaro.getSexo().equals("")) {
            return "redirect:/passaros";
        } else if (!passaro.getSexo().equals("M") && !passaro.getSexo().equals("F")) {
            return "redirect:/passaros";
        }
        Authentication credentials = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = userRepository.findByEmail(credentials.getName());

        List<Passaro> passaros = usuario.getPassaro();
        passaros.add(passaro);
        usuario.setPassaro(passaros);
        passaroRepository.save(passaro);
        userRepository.save(usuario);
        return "redirect:/passaros";
    }

    @RequestMapping(value = "/locacao{id}", method = RequestMethod.GET)
    public ModelAndView locacaoGET(@PathVariable("id") long id) {
        Authentication credentials = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = userRepository.findByEmail(credentials.getName());

        Gaiola gaiola = gaiolaRepository.findById(id).get();
        List<Gaiola> gaiolas = gaiolaRepository.findAll();
        List<Passaro> passaros = usuario.getPassaro();
        ModelAndView mv = new ModelAndView("/locacao");
        mv.addObject("gaiola", gaiola);
        mv.addObject("gaiolas", gaiolas);
        mv.addObject("passaros", passaros);
        return mv;
    }

    @RequestMapping(value = "/locacao{id}", method = RequestMethod.POST)
    public String locacao(@Valid Passaro passaro, @PathVariable("id") long id) {
        Authentication credentials = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = userRepository.findByEmail(credentials.getName());

        System.out.println("========" + passaro);
        List<Gaiola> gaiolas = usuario.getGaiola();
        Passaro passaroId = passaroRepository.findById(passaro.getId());
        gaiolas.forEach(gaiola -> {
            if (gaiola.getId() == id) {
                List<Passaro> gaiolasList = gaiola.getPassaro();
                gaiolasList.add(passaroId);
                gaiola.setPassaro(gaiolasList);
                passaroId.setGaiola(gaiola);
                gaiolaRepository.save(gaiola);

            }
        });

        passaroRepository.save(passaroId);
        usuario.setGaiola(gaiolas);
        userRepository.save(usuario);
        return "redirect:/";


    }

    @RequestMapping(value = "/descgaiola{id}", method = RequestMethod.GET)
    public ModelAndView descGaiola(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("/descgaiola");
        Gaiola gaiola = gaiolaRepository.findById(id).get();
        List<Passaro> passaros = gaiola.getPassaro();
        mv.addObject("passaros", passaros);
        mv.addObject("gaiola", gaiola);

        return mv;
    }

    @RequestMapping(value = "/descgaiola{id}", method = RequestMethod.POST)
    public String descGaiolaPOST(@Valid Gaiola gaiola, @PathVariable("id") long id) {
        Gaiola gaiolaEDIT = gaiolaRepository.findById(id).get();
        gaiolaEDIT.setNumeroFilhotes(gaiola.getNumeroFilhotes());
        gaiolaEDIT.setNotacao(gaiola.getNotacao());
        gaiolaRepository.save(gaiolaEDIT);
        return "redirect:/";
    }

    @RequestMapping("/deletarPassaro{passaro.id}")
    public String deletarPassaro(@PathVariable("passaro.id") long id) {
        Authentication credentials = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = userRepository.findByEmail(credentials.getName());

        Passaro passaro = passaroRepository.findById(id);
        List<Passaro> listaPassaro = usuario.getPassaro();
        listaPassaro.remove(passaro);
        userRepository.save(usuario);
        passaroRepository.delete(passaro);
        return ("redirect:/passaros");
    }

    @RequestMapping("/deletarGaiola{gaiola.id}")
    public String deletarGaiola(@PathVariable("gaiola.id") long id) {
        Authentication credentials = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = userRepository.findByEmail(credentials.getName());

        Gaiola gaiola = gaiolaRepository.findById(id).get();
        List<Gaiola> gaiolaPassaro = usuario.getGaiola();
        List<Passaro> gaioalPas = gaiola.getPassaro();
        if (gaioalPas.size() != 0) {
            return "redirect:/";
        }
        gaiola.setPassaro(gaioalPas);
        gaiolaPassaro.remove(gaiola);
        userRepository.save(usuario);
        gaiolaRepository.delete(gaiola);
        return ("redirect:/gaiola");
    }

    @RequestMapping("/removerPassaro{passaro.id}")
    public String removerPassaro(@PathVariable("passaro.id") long id) {
        Authentication credentials = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = userRepository.findByEmail(credentials.getName());


        Passaro passaro = passaroRepository.findById(id);
        List<Gaiola> gaiolas = usuario.getGaiola();

        gaiolas.forEach(gaiola -> {
            if (gaiola.getNumero() == id) {
                List<Passaro> passaros = gaiola.getPassaro();
                passaros.remove(passaro);
            }
        });

        usuario.setGaiola(gaiolas);
        userRepository.save(usuario);
        passaro.setGaiola(null);
        passaroRepository.save(passaro);

        return "redirect:/";
    }

}
