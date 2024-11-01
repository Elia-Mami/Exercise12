package p12.exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class MultiQueueImpl<T, Q> implements MultiQueue<T, Q>{

    private Map<Q, Queue<T>> multiQueue; 

    public MultiQueueImpl(){
        multiQueue = new HashMap<Q, Queue<T>>();
    }

    @Override
    public Set<Q> availableQueues() {
        try{
            return multiQueue.keySet();
        }
        catch(Exception e){
            throw e;
        }
    }

    @Override
    public void openNewQueue(Q queue) {
        if(multiQueue.containsKey(queue)){
            throw new IllegalArgumentException("Queue is already available");
        }
        else{
            multiQueue.put(queue, new LinkedList<>());
        }
    }

    @Override
    public boolean isQueueEmpty(Q queue) {
        if(!multiQueue.containsKey(queue)){
            throw new IllegalArgumentException("Queue is not available");
        }
 
        return multiQueue.get(queue).isEmpty();
    }

    @Override
    public void enqueue(T elem, Q queue) {
        if(!multiQueue.containsKey(queue)){
            throw new IllegalArgumentException("Queue is not available");
        }
    
        multiQueue.get(queue).add(elem);
    }

    @Override
    public T dequeue(Q queue) {
        if(!multiQueue.containsKey(queue)){
            throw new IllegalArgumentException("Queue is not available");
        }
            
        return multiQueue.get(queue).poll();
    }

    @Override
    public Map<Q, T> dequeueOneFromAllQueues() {
        Map<Q, T> dequeuedElemets = new HashMap<>();
        
        for(Q currentKey : multiQueue.keySet()){
            dequeuedElemets.put(currentKey, multiQueue.get(currentKey).poll());
        }

        return dequeuedElemets;
    }

    @Override
    public Set<T> allEnqueuedElements() {
        Set<T> enqueuedElements = new HashSet<>();
        
        for(Q currentKey : multiQueue.keySet()){
            for(T currentElement : multiQueue.get(currentKey)){
                enqueuedElements.add(currentElement);
            }
        }
        
        return enqueuedElements;
    }

    @Override
    public List<T> dequeueAllFromQueue(Q queue) {
        if(!multiQueue.containsKey(queue)){
            throw new IllegalArgumentException("Queue is not available");
        }

        List<T> dequeuedElements = new ArrayList<>();
        for(T currentElement : multiQueue.get(queue)){
            dequeuedElements.add(currentElement);
        }

        multiQueue.get(queue).clear();
        return dequeuedElements;
    }

    @Override
    public void closeQueueAndReallocate(Q queue) {
        if(!multiQueue.containsKey(queue)){
            throw new IllegalArgumentException("Queue is not available");
        }

        Queue<T> temp = new LinkedList<T>();

        for(T currentElement : multiQueue.get(queue)){
            temp.add(currentElement);
        }

        multiQueue.remove(queue);

        if(multiQueue.keySet().isEmpty()){
            throw new IllegalStateException();
        }

        Q nextKey = multiQueue.keySet().iterator().next();

        for(T currentElement : temp){
            multiQueue.get(nextKey).add(currentElement);
        }
    }

}
