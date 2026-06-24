package com.hrm.hrm_system.modules.user;

import com.hrm.hrm_system.common.exception.AppException;
import com.hrm.hrm_system.common.utils.UUIDHelper;
import com.hrm.hrm_system.modules.user.dtos.CreateIUserInputDTO;
import com.hrm.hrm_system.common.utils.StringHelper;
import com.hrm.hrm_system.modules.user.dtos.SearchUserFilters;
import com.hrm.hrm_system.modules.user.dtos.UpdateIUserInputDTO;
import com.hrm.hrm_system.modules.user.dtos.IUserPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public UserService(UserRepository repository){
        this.userRepository=repository;
    }

    // EXPORT METHODS ================================================= >
    // SET
    public UserEntity set(UserEntity entity, IUserPayload model){
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
        if(model instanceof  UpdateIUserInputDTO updateModel){
            if(updateModel.getIsLocked()!=null){
                entity.setIsLocked(updateModel.getIsLocked());
            }
            if(updateModel.getIsDeleted()!=null){
                entity.setIsDeleted(updateModel.getIsDeleted());
            }
        }
        return entity;
    }

    //GET
    public Optional<UserEntity> get(String keyword){

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
    public List<UserEntity> search(SearchUserFilters filters){
        System.out.println(filters.getIsLocked());
        System.out.println(filters.getNameKeyword());
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
        if(filters.getIsLocked()!=null){
            spec=spec.and((r,q,cb)->
                    cb.equal(
                            r.get("isLocked"),
                            filters.getIsLocked()
                    ));
        }
        // 3: for isDeleted
        if(filters.getIsDeleted()!=null){
            spec=spec.and((r,q,cb)->
                    cb.equal(
                            r.get("isDeleted"),
                            filters.getIsDeleted()
                    ));
        }


        List<UserEntity> users= userRepository.findAll(spec);

        return users;
    }

    // CREATE
    public UserEntity create(CreateIUserInputDTO model){

        // check if user already exits  with incoming email
        Optional<UserEntity> entity= this.get(model.getEmail());
        if(entity.isPresent()){
            throw  new AppException("User already exists with this email",HttpStatus.BAD_REQUEST);
        }
        //create new user
        UserEntity newUser=new UserEntity();

        newUser.setEmail(model.getEmail());
        newUser.setId(uuidHelper.generate());

        newUser=this.set(newUser,model);

        return userRepository.save(newUser);
    }

    // UPDATE
    public UserEntity update(String id,UpdateIUserInputDTO model){

        Optional<UserEntity> entity = this.get(id);

        if(entity.isEmpty()){
            throw new AppException("User not found",HttpStatus.NOT_FOUND);
        }

        UserEntity user=this.set(entity.get(),model);
        user=this.userRepository.save(user);
        return user;
    }

}
