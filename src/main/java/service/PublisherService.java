package service;

import model.Publisher;
import repository.PublisherRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PublisherService {

    private PublisherRepository publisherRepository;
    private static PublisherService instance;

    private PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public static PublisherService getInstance() {
        if (instance == null) {
            instance = new PublisherService(PublisherRepository.getInstance());
        }
        return instance;
    }
    /**
     * log eklenilsin
     */
    public void savePublisher(String name, LocalDate creatDate) {

        Publisher publisher = new Publisher(name, creatDate);
        publisherRepository.save(publisher);

    }

    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public Optional<Publisher> getByName(String publisherName) {
        return getAllPublishers().stream()
                .filter(publisher -> publisher.getName().equals(publisherName))
                .findFirst();
    }
}
