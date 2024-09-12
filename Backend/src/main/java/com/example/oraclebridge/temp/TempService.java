package com.example.oraclebridge.temp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TempService {
    @Autowired
    private TempRepository tempRepository;

    public TempService(TempRepository tempRepository) {
        this.tempRepository = tempRepository;
    }
     public List<Temp> getAll(){
        return tempRepository.findAll();
     }
     public Temp getById(Long id){
        return tempRepository.findById(id).orElse(null);
     }
     public Temp addTemp(Temp temp){
        return tempRepository.save(temp);
     }
     public Temp updateTemp(Long id, Temp temp) {
        return tempRepository.save(temp);
     }
     public void deleteTemp(Long id){
         tempRepository.deleteById(id);
     }
}
