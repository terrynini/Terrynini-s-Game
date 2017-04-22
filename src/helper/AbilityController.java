package helper;

import entity.Ability;
import entity.Creature;
import entity.Entity;

import java.awt.Graphics;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AbilityController {
	private static ConcurrentLinkedQueue<Ability> queue = new ConcurrentLinkedQueue<Ability>();
	private static ConcurrentLinkedQueue<Creature> equeue = new ConcurrentLinkedQueue<Creature>();
	public static void put(Ability a ,Creature e){
		queue.add(a);
		equeue.add(e);
	}
	public static void render(Graphics g){
		while(!queue.isEmpty()){
			queue.poll().render(g, equeue.poll());
		}
	}
}
