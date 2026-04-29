package com.redhat.tintin;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/api/tintin/cover")
public class CoverController {

    private static final String DESCRIBE_SYSTEM =
            "You are an art critic and Tintin expert analyzing book covers by Herge.";

    private static final String ART_STYLE_SYSTEM =
            "You are an art historian specializing in Belgian comics and the ligne claire style.";

    private static final Resource BUNDLED_IMAGE = new ClassPathResource("static/images/tintin-image.png");

    private final ChatClient chatClient;

    public CoverController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    record CoverRequest(String url) {}

    @PostMapping(path = "/describe", produces = MediaType.TEXT_PLAIN_VALUE)
    public String describeCover(@RequestBody(required = false) CoverRequest request) throws MalformedURLException {
        Resource image = resolveImage(request);
        return chatClient.prompt()
                .system(DESCRIBE_SYSTEM)
                .user(u -> u
                        .text("""
                                Describe this Tintin book cover in detail, including the artistic style,
                                characters shown, setting, and what adventure it might depict.
                                """)
                        .media(MimeTypeUtils.IMAGE_PNG, image))
                .call()
                .content();
    }

    @PostMapping(path = "/analyze", produces = MediaType.TEXT_PLAIN_VALUE)
    public String analyzeArtStyle(@RequestBody(required = false) CoverRequest request) throws MalformedURLException {
        Resource image = resolveImage(request);
        return chatClient.prompt()
                .system(ART_STYLE_SYSTEM)
                .user(u -> u
                        .text("""
                                Analyze the artistic techniques and color palette used in this comic book cover.
                                Discuss the use of ligne claire (clear line) technique characteristic of Herge's work.
                                """)
                        .media(MimeTypeUtils.IMAGE_PNG, image))
                .call()
                .content();
    }

    private Resource resolveImage(CoverRequest request) throws MalformedURLException {
        if (request != null && request.url() != null && !request.url().isBlank()) {
            return new UrlResource(request.url());
        }
        return BUNDLED_IMAGE;
    }
}
