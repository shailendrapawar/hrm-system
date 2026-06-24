package com.hrm.hrm_system.modules.user;

import com.hrm.hrm_system.common.utils.UUIDHelper;
import com.hrm.hrm_system.modules.user.dtos.CreateIUserInputDTO;
import com.hrm.hrm_system.common.utils.StringHelper;
import com.hrm.hrm_system.modules.user.dtos.UpdateIUserInputDTO;
import com.hrm.hrm_system.modules.user.dtos.IUserPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

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
            throw new RuntimeException("Invalid identifier");
        }

        Optional<UserEntity> user;

        if(uuidHelper.isValid(keyword)){
            user=userRepository.findById(keyword);
        }else if (stringHelper.isValidEmail(keyword)){
            user=userRepository.findByEmail(keyword);
        }else{
            throw new RuntimeException("Invalid ID format");
        }
        return user;
    }

    // SEARCH
    public List<UserEntity> search(SearchUserFilters filters){
        //init where clause
        Specification<UserEntity> spec= Specification.where((r,q,cb)->cb.conjunction());

        //1: for name
        if(StringUtils.hasText(filters.getNameKeyword())){
            spec=spec.and((r,q,cb)->
                    cb.or(
                            cb.like(r.get("firstName"), "%"+filters.getNameKeyword()+"%"),
                            cb.like(r.get("lastName"), "%"+filters.getNameKeyword()+"%")
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

        UserEntity newUser=new UserEntity();

        newUser.setId(uuidHelper.generate());

        newUser=this.set(newUser,model);

        return userRepository.save(newUser);
    }

    // UPDATE
    public UserEntity update(String id,UpdateIUserInputDTO model){

        Optional<UserEntity> entity =this.get(id);

        if(entity.isEmpty()){
            throw new RuntimeException("User not found");
        }

        UserEntity user=this.set(entity.get(),model);
        user=this.userRepository.save(user);
        return user;
    }

}
