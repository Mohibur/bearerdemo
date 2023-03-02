package simple.mind.bearer.ctrl.simple;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/api/v1/asimple")
public class ASimpleCtrl {
    @lombok.Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Data {
        public String from;
        public String msg;

    }

    @GetMapping
    public ResponseEntity<Data> authenticate() {
        return ResponseEntity.ok(new Data("Me", "To You"));
    }

}
