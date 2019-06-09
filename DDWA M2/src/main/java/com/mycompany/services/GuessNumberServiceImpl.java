package com.mycompany.services;

import com.mycompany.daos.GameDao;
import com.mycompany.daos.RoundDao;
import com.mycompany.dtos.Game;
import com.mycompany.dtos.Round;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author angela997
 */
@Service
public class GuessNumberServiceImpl implements GuessNumberService {

    @Autowired
    GameDao gdao;
    @Autowired
    RoundDao rdao;
    String answer;
    //Map<Integer,Game> allGames;

    //@Autowired
    public GuessNumberServiceImpl() {
        answer = "0000";

    }

    @Override
    public List<Game> getAllGames() {
        return gdao.ReadAll();
    }

    @Override
    public Game getGameById(int gameId) {
        return gdao.ReadById(gameId);
    }

    @Override
    public Game beginGame() //creates a game
    {
        Game g1 = new Game();
        String answer = this.getUniqueAnswer();
        g1.setAnswer(answer);

        return gdao.Create(g1);
    }

    private String getUniqueAnswer() {
        //make it a String or an ArrayList
        String availableDigits = "0123456789"; //A string is actually an array of chars
        //the possible permutations of a bulls and cows game will be 10!/6!=10*9*8*7 possibilities
        String answer = ""; //strings are immutable
        for (int i = 0; i < 4; i++) //traverses answer array of length 4
        //no () for length since an array is not an object but rather a data structure
        {
            Random r = new Random();
            double r1 = r.nextDouble();
            int index = (int) (r1 * availableDigits.length()); //chooses random index, takes that digit as a position in the exact answer match
            answer = answer + availableDigits.charAt(index);
            availableDigits = availableDigits.substring(0, index) + availableDigits.substring(index);
        }
        return answer + "";
    }

    @Override
    public String takeAGuess(int gameId, String guess) //user takes inputs a guess userIO IO
    {
        //UserIO i1=new UserIOConsoleImpl(); 
        Round ri = new Round();
        Game g1 = gdao.ReadById(gameId);
        String result = this.compareGuess(gameId, guess, g1.getAnswer());
        ri.setResult(result);
        ri.setGuess(guess);
        ri.setGameId(gameId);
        rdao.Create(ri);

        return result;
    }

    @Override
    public String compareGuess(int gameId, String guess, String answer) {
        String result = "";//e:ep:p";
        int e = 0;//exact counter
        int p = 0;//partial counter
        for (int i = 0; i < guess.length(); i++) //test for valid guess, length 4 etc, guess not valid etc
        {
            //traverse the chars of the guess and compare to the unique answer, update exact and partial matches for each digit
            char g = guess.charAt(i);
            char a = answer.charAt(i);
            if (a == g) {
                //scope of p and e variables is per round
                e++;
            } else {
                for (int j = 0; j < answer.length(); j++) {
                    a = answer.charAt(j);
                    if (g == a) {
                        p++; //partial correct digits
                    }
                }
            }

        }
        //update round result
        result = "e:" + e + "p:" + p;
        if (e == 4) {
            //System.out.println("you won!");
            Game go=(gdao.ReadById(gameId));
            go.setGameStatus(true);
            gdao.Update(go.getGameId(), go);
        }
        return result;
    }

    @Override
    public List<Round> getRounds(int gameId) {
        return rdao.ReadByGameId(gameId);
    }

}
