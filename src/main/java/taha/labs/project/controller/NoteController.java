package taha.labs.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import taha.labs.project.entity.Note;
import taha.labs.project.model.dto.NoteDto;
import taha.labs.project.service.NoteService;

import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String listNotes(Model model, Principal principal) {
        List<Note> notes = noteService.getUserNotes(principal.getName());
        model.addAttribute("notes", notes);
        return "notes/list"; 
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        if (!model.containsAttribute("noteDto")) {
            model.addAttribute("noteDto", new NoteDto());
        }
        return "notes/create";
    }

    @PostMapping
    public String createNote(@Valid @ModelAttribute("noteDto") NoteDto noteDto,
                             BindingResult bindingResult,
                             Principal principal,
                             RedirectAttributes redirectAttributes) { 
        if (bindingResult.hasErrors()) {
            return "notes/create";
        }
        
        noteService.createNote(noteDto, principal.getName());
        
        redirectAttributes.addFlashAttribute("success", "Note created! Go to the list page to see your note!");
        
        return "redirect:/notes/new"; 
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, Principal principal) {
        Note note = noteService.getUserNotes(principal.getName())
                               .stream()
                               .filter(n -> n.getId().equals(id))
                               .findFirst()
                               .orElseThrow(() -> new RuntimeException("Note not found"));

        NoteDto dto = new NoteDto();
        dto.setTitle(note.getTitle());
        dto.setContent(note.getContent());
        model.addAttribute("noteDto", dto);
        model.addAttribute("noteId", id);
        return "notes/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateNote(@PathVariable Long id,
                             @Valid @ModelAttribute("noteDto") NoteDto noteDto,
                             BindingResult bindingResult,
                             Principal principal) {
        if (bindingResult.hasErrors()) {
            return "notes/edit";
        }
        noteService.updateNote(id, noteDto, principal.getName());
        return "redirect:/notes";
    }

    @PostMapping("/delete/{id}")
    public String deleteNote(@PathVariable Long id, Principal principal) {
        noteService.deleteNote(id, principal.getName());
        return "redirect:/notes";
    }
}