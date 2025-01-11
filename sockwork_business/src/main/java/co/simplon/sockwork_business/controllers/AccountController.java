package co.simplon.sockwork_business.controllers;

import co.simplon.sockwork_business.dtos.AccountCreate;
import co.simplon.sockwork_business.dtos.AuthInfo;
import co.simplon.sockwork_business.entities.AccountEntity;
import co.simplon.sockwork_business.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;
    //Injection de dépendance. Il a injecté un bean (notre service)
    //On peut faire du lazyInitialisation => quand il y a certains bean dont on a pas besoin au démarrage
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/log-in")
    @ResponseStatus(HttpStatus.CREATED)
    AuthInfo getAccountById(@RequestBody @Valid AccountCreate inputs) {
       return accountService.getAccountById(inputs);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody AccountCreate inputs){
        accountService.create(inputs);

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
     public String get(){
        String string = "Hello World!";
        return string;
    }

    @GetMapping("/with-role")
    Object withRole(){
        return "with roles";
    }

    @GetMapping("/entity/{id}")
    @ResponseStatus(HttpStatus.OK)
    AccountEntity getEntityById(@PathVariable @Valid Long id){
        return accountService.getEntity(id);
    }
}

                ////////Proxy///////
//Intermédiaire
//1 proxy par service/repo/controller/etc
//A l'execution il va créer une classe pour chacun des services/repo etc
//il va proxifié uniquement quand on lui ajoute des caches ou bien des transactions
//Il va propager l'évènement
//Capacité de réflexion/ introspection
//Dans le monde informatique => serveur qui va filtrer notre requête envers un autre serveur
