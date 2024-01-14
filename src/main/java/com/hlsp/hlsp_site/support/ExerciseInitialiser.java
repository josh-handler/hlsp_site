// package com.hlsp.hlsp_site.support;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.ApplicationArguments;
// import org.springframework.boot.ApplicationRunner;
// import org.springframework.stereotype.Component;

// import com.hlsp.hlsp_site.model.Exercise;
// import com.hlsp.hlsp_site.repository.ExerciseRepository;


// @Component
// public class ExerciseInitialiser implements ApplicationRunner{
//     //This class enters some initial exercises into the system
//     //The original design was for users to create there own exercises on the front end
//     //but between trouble getting spring boot to accept variable sized forms and
//     //time issues that wasn't possible


//     @Autowired
//     ExerciseRepository exerciseRepository;

//     @Override
//     public void run(ApplicationArguments args){

//         Exercise aerobic = new Exercise("Aerobic",Exercise.Type.AEROBIC);
//         Exercise strength = new Exercise("Strength",Exercise.Type.STRENGTH);
//         Exercise stretch = new Exercise("Stretch",Exercise.Type.STRETCH);
//         Exercise balance = new Exercise("balance",Exercise.Type.BALANCE);
//         Exercise mixed = new Exercise("Mixed",Exercise.Type.MIXED);

//         exerciseRepository.save(aerobic);
//         exerciseRepository.save(strength);
//         exerciseRepository.save(stretch);
//         exerciseRepository.save(balance);
//         exerciseRepository.save(mixed);
//     }
    
// }
