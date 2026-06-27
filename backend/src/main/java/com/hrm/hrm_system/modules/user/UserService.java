package com.hrm.hrm_system.modules.user;

import com.hrm.hrm_system.common.dtos.AppContext;
import com.hrm.hrm_system.common.exception.AppException;
import com.hrm.hrm_system.common.utils.JWTHelper;
import com.hrm.hrm_system.common.utils.UUIDHelper;
import com.hrm.hrm_system.modules.user.dtos.*;
import com.hrm.hrm_system.common.utils.StringHelper;
import com.hrm.hrm_system.modules.user.enums.UserDefaultRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private  UUIDHelper uuidHelper;
    @Autowired
    private StringHelper stringHelper;
    @Autowired
    private JWTHelper jwtHelper;

    public UserService(UserRepository repository){
        this.userRepository=repository;
    }

    // EXPORT METHODS ================================================= >
    // SET
    public UserEntity set(UserEntity entity, IUserPayload model, AppContext context){
        if(StringUtils.hasText(model.getFirstName())){
            entity.setFirstName(model.getFirstName());
        }
        if(StringUtils.hasText(model.getLastName())){
            entity.setLastName(model.getLastName());
        }
        if(StringUtils.hasText(model.getEmail())){
            entity.setEmail(model.getEmail());
        }
        if(StringUtils.hasText(model.getPassword())){
            //hash password and check conditions for password update
            entity.setPassword(model.getPassword());
        }

        // only update schema fields
        if(model instanceof  UpdateUserInputDTO updateModel){
            if(updateModel.getStatus()!=null){
                entity.setStatus(updateModel.getStatus());
            }
        }
        return entity;
    }

    //GET
    public Optional<UserEntity> get(String keyword,AppContext context){

        if(!StringUtils.hasText(keyword)) {
            throw new AppException("Invalid identifier", HttpStatus.BAD_REQUEST);
        }

        Optional<UserEntity> user;

        if(uuidHelper.isValid(keyword)){
            user=userRepository.findById(keyword);
        }else if (stringHelper.isValidEmail(keyword)){
            user=userRepository.findByEmail(keyword);
        }else{
            throw new AppException("Invalid ID format",HttpStatus.BAD_REQUEST);
        }
        return user;
    }

    // SEARCH
    public List<UserEntity> search(SearchUserFilters filters,AppContext context){

        //init where clause
        Specification<UserEntity> spec= Specification.where((r,q,cb)->cb.conjunction());

        //1: for name
        if(StringUtils.hasText(filters.getNameKeyword())){
            String keyword="%" + filters.getNameKeyword().toLowerCase() + "%";
            spec=spec.and((r,q,cb)->
                    cb.or(
                            cb.like(cb.lower(r.get("firstName")), keyword),
                            cb.like(cb.lower(r.get("lastName")), keyword)
                    ));
        }
        // 2: for isLocked
        if(filters.getStatus()!=null){
            spec=spec.and((r,q,cb)->
                    cb.equal(
                            r.get("status"),
                            filters.getStatus()
                    ));
        }



        List<UserEntity> users= userRepository.findAll(spec);

        return users;
    }

    // CREATE
    public UserEntity create(CreateUserInputDTO model,AppContext context){

        // check if user already exits  with incoming email
        Optional<UserEntity> entity= this.get(model.getEmail(),context);
        if(entity.isPresent()){
            throw  new AppException("User already exists with this email",HttpStatus.BAD_REQUEST);
        }
        //create new user
        UserEntity newUser=new UserEntity();

        // set initial values
        newUser.setId(uuidHelper.generate());

        //set default application roles
        ArrayList<String> roles= new ArrayList<>();
        roles.add(UserDefaultRole.USER.toString().toLowerCase());

        newUser.setRoles(roles);

        newUser=this.set(newUser,model, context);

        return userRepository.save(newUser);
    }

    // UPDATE
    public UserEntity update(String id, UpdateUserInputDTO model,AppContext context){

        Optional<UserEntity> entity = this.get(id,context);

        if(entity.isEmpty()){
            throw new AppException("User not found",HttpStatus.NOT_FOUND);
        }

        UserEntity user=this.set(entity.get(),model,context);
        user=this.userRepository.save(user);
        return user;
    }


}
