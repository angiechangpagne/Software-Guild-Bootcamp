package services;
import Daos.DirectorDao;
import Daos.DvdDao;
import java.util.List;
import daos.*;
//import com.mycompany.dvdlibrary.daos.DvdDao;
import dtos.*;
import dtos.Director;
import org.springframework.stereotype.Service;
//import com.mycompany.dvdlibrary.dtos.Dvd;
@Service
public class DvdServiceImpl implements DvdService {

    DvdDao dvdDao;
    DirectorDao directorDao;

    public DvdServiceImpl(DvdDao dvdDao, DirectorDao directorDao) {
        this.dvdDao = dvdDao;
        this.directorDao = directorDao;
    }

    @Override
    public Director CreateDirector(String name) {
        Director director = new Director();
        director.setName(name);
        directorDao.Create(director);
        return director;
    }

    @Override
    public List<Director> ReadAllDirectors() {
        return directorDao.ReadAll();
    }

    @Override
    public Director ReadDirectorById(int directorId) {
        Director director = directorDao.ReadById(directorId);
        if (director != null) {
            director.setDvds(dvdDao.ReadByDirectorId(directorId));
        }
        return director;
    }

    @Override
    public void UpdateDirector(int directorId, Director director) {
        directorDao.Update(directorId, director);
    }

    @Override
    public void DeleteDirector(int directorId) {
        directorDao.Delete(directorId);
    }

    @Override
    public Dvd CreateDvd(Dvd dvd) throws DirectorNotFoundException {
        Director director = directorDao.ReadById(dvd.getDirectorId());
        if (director != null) {
            return dvdDao.Create(dvd);
        }
        throw new DirectorNotFoundException("Director not found", dvd);
    }

    @Override
    public List<Dvd> ReadAllDvds() {
        return this.dvdDao.ReadAll();
    }

    @Override
    public Dvd ReadDvdById(int dvdId) {
        return this.dvdDao.ReadById(dvdId);
    }

    @Override
    public List<Dvd> ReadDvdsByDirectorId(int directorId) {
        return dvdDao.ReadByDirectorId(directorId);
    }

    @Override
    public void UpdateDvd(int dvdId, Dvd dvd) throws DirectorNotFoundException {
        Director director = directorDao.ReadById(dvd.getDirectorId());
        if (director == null) {
            throw new DirectorNotFoundException("Director not found", dvd);
        }
        this.dvdDao.Update(dvdId, dvd);
    }

    @Override
    public void DeleteDvd(int dvdId) {
        this.dvdDao.Delete(dvdId);
    }

}