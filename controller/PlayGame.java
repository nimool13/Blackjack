package BlackJack.controller;

import BlackJack.model.IObserver;
import BlackJack.view.IView;
import BlackJack.model.Game;
import BlackJack.view.IView.operation;

import java.util.Scanner;

public class PlayGame implements IObserver {
  private Game m_game;
  private IView m_view;
  private int latency = 1000;

  public PlayGame (Game x_game, IView x_view){
    m_game =x_game;
    m_view=x_view;
    save(this);
  }
  //@SuppressWarnings("resource")
  public boolean Play() {
    m_view.DisplayWelcomeMessage();
    m_view.DisplayDealerHand(m_game.GetDealerHand(), m_game.GetDealerScore());
    m_view.DisplayPlayerHand(m_game.GetPlayerHand(), m_game.GetPlayerScore());
    if (m_game.IsGameOver()) {
      m_view.DisplayGameOver(m_game.IsDealerWinner());
    }
    m_view.GetInput();
    Scanner sc = new Scanner(System.in);
    try {
      System.out.println("");
      int choice = sc.nextInt();
      operation input = operation.values()[choice];
      switch (input) {
        case Play:
          m_game.NewGame();
          break;
        case Hit:
          m_game.Hit();
          break;
        case Stand:
          m_game.Stand();
          break;
        case Quit:
          return false;
      }
    } catch (Exception e) {
      System.out.println("Warning: invalid entry");
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e1) {
        System.out.println("System Crash!");
        e1.printStackTrace();
        System.exit(0);
      }
    }
  return true;
  }


  @Override
  public void update(int a_delay) {

    try {
      m_view.DisplayDealingCard();
      Thread.sleep(a_delay);
      m_view.DisplayDealerHand(m_game.GetDealerHand(), m_game.GetDealerScore());
      m_view.DisplayDealingCard();
      Thread.sleep(a_delay);
      m_view.DisplayPlayerHand(m_game.GetPlayerHand(), m_game.GetPlayerScore());
      Thread.sleep(a_delay);
    } catch (InterruptedException e) {
      System.out.println("System crash error");
      e.printStackTrace();
      System.exit(-1);
    }

  }

//  @Override
//  public void infoToObservers() {
//    System.out.println("--------infoToObservers----------");
//    try {
//      Thread.sleep(latency);
//      m_view.DisplayDealerHand(m_game.GetDealerHand(), m_game.GetDealerScore());
//      m_view.DisplayPlayerHand(m_game.GetPlayerHand(), m_game.GetPlayerScore());
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
//  }


  public void save(IObserver a_observer) {
    this.m_game.save(a_observer);
  }


}