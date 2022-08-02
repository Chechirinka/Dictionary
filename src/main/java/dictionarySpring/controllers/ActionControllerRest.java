package dictionarySpring.controllers;

import dictionarySpring.configuration.DictionaryType;
import dictionarySpring.exception.TypeNotFoundException;
import dictionarySpring.model.DictionaryLine;
import dictionarySpring.service.DictionaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/action-rest")
@Tag(name = "Action", description = "REST controller for action")
public class ActionControllerRest{
    private final static String NO_EXIST_LANGUAGE = "Error, this language does not exist!";
    private final static String SUCCESS = "Success";
    public final static String ERROR = "Error";
    private final static String DELETE = "Delete";
    private final static String NO_DELETE = "No delete";
    private final DictionaryService dictionaryService;
    private DictionaryType selectedDictionary;

    @Autowired
    public ActionControllerRest(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @PostMapping("add")
    @Operation(summary = "Add", description = "Add something", tags = {"Add"})
    public ResponseEntity<?> add(@RequestParam(value = "dictionaryId") int dictionaryId,
                                 @RequestBody DictionaryLine dictionaryLine) {
        try {
            selectedDictionary = DictionaryType.getDictionaryTypeByNumber(dictionaryId);
        } catch (TypeNotFoundException e) {
            return new ResponseEntity<>(NO_EXIST_LANGUAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (dictionaryService.addService(dictionaryLine.getKey(), dictionaryLine.getValue(), selectedDictionary)) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<>(ERROR, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("read")
    @Operation(summary = "Read", description = "Read content", tags = {"Read"})
    public ResponseEntity<?> read(@RequestParam(value = "dictionaryId") int dictionaryId) {
        try {
            selectedDictionary = DictionaryType.getDictionaryTypeByNumber(dictionaryId);
        } catch (TypeNotFoundException e) {
            return new ResponseEntity<>(NO_EXIST_LANGUAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(dictionaryService.readServiceRest(selectedDictionary), HttpStatus.OK);
    }


    @GetMapping("search")
    @Operation(summary = "Search", description = "Search something", tags = {"Search"})
    public ResponseEntity<?> search(@RequestParam(value = "dictionaryId") int dictionaryId,
                                 @RequestParam(value = "key") String key) {
        try {
            selectedDictionary = DictionaryType.getDictionaryTypeByNumber(dictionaryId);
        } catch (TypeNotFoundException e) {
            return new ResponseEntity<>(NO_EXIST_LANGUAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return dictionaryService.searchServiceRest(key, selectedDictionary);
    }

    @PostMapping("remove")
    @Operation(summary = "Remove", description = "Remove something", tags = {"Remove"})
    public ResponseEntity<?> remove(@RequestParam(value = "dictionaryId") int dictionaryId,
                                    @RequestParam(value = "key") String key){
        try {
            selectedDictionary = DictionaryType.getDictionaryTypeByNumber(dictionaryId);
        } catch (TypeNotFoundException e) {
            return new ResponseEntity<>(NO_EXIST_LANGUAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (dictionaryService.removeService(key, selectedDictionary)) {
            return new ResponseEntity<>(DELETE, HttpStatus.OK);
        }
        return new ResponseEntity<>(NO_DELETE, HttpStatus.BAD_REQUEST);
    }
}