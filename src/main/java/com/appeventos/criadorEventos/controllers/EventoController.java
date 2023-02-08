package com.appeventos.criadorEventos.controllers;
import com.appeventos.criadorEventos.interfaces.ConvidadoRepository;
import com.appeventos.criadorEventos.interfaces.EventosRepository;
import com.appeventos.criadorEventos.models.Convidado;
import com.appeventos.criadorEventos.models.Evento;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class EventoController {
    @Autowired
    EventosRepository er;
    @Autowired
    ConvidadoRepository cr;

    @RequestMapping(value = "/cadastrar", method = RequestMethod.GET)
    public String cadastrarEvento(){
        return "formEvento.html";
    }

    @RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
    public String cadastrarEvento(@Valid @ModelAttribute Evento evento, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()){
            attributes.addFlashAttribute("mensagem","Erro, os campos não podem ser vazios");
            return "redirect:/cadastrar";
        }
    attributes.addFlashAttribute("mensagem", "Evento adicionado com sucesso!");
    er.save(evento);
    return "redirect:/cadastrar";
    }
    @RequestMapping(value = "/listaEventos")
    public ModelAndView listaEventos(){
        ModelAndView mv = new ModelAndView("index");
        Iterable<Evento> eventos = er.findAll();
        mv.addObject("eventos", eventos);
        return mv;
    }
    @RequestMapping(value = "/deletarEvento")
    public String deletarEvento(long codigo){
        Evento evento = er.findByCodigo(codigo);
        Iterable<Convidado> convidados = cr.findByEvento(evento);
        cr.deleteAll(convidados);
        er.delete(evento);
        return "redirect:/listaEventos";
    }

    @RequestMapping(value = "/deletarConvidado")
    public String deletarConvidado(String rg){
        Convidado convidado = cr.findByRg(rg);
        Evento evento = convidado.getEvento();
        cr.delete(convidado);
        String codigo = String.valueOf(evento.getCodigo());
        return "redirect:/"+codigo;
    }

    @RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
    public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo){
        Evento evento = er.findByCodigo(codigo);
        ModelAndView mv = new ModelAndView("detalhesEvento.html");
        mv.addObject("evento", evento);

        Iterable <Convidado> convidados = cr.findByEvento(evento);
        mv.addObject("convidados", convidados);

        return mv;
    }
    @RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
    public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid @ModelAttribute("convidado") Convidado convidado, BindingResult result, RedirectAttributes attributes){
        boolean errors = result.hasErrors();
        if (errors){
            attributes.addFlashAttribute("mensagem","Verifique os campos!");
            return "redirect:/{codigo}";
        }
        Evento evento = er.findByCodigo(codigo);
        convidado.setEvento(evento);
        cr.save(convidado);
        attributes.addFlashAttribute("mensagem","Convidado adicionado!");
        return "redirect:/{codigo}";
    }

}
