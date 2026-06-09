import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class EmetteurAgent extends Agent {
    private static final long serialVersionUID = 1L;

    protected void setup() {
        addBehaviour(new TickerBehaviour(this, 5000) {
            int counter = 0;

            protected void onTick() {
                // Search DF for "Imprimante" service
                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType("Imprimante");
                template.addServices(sd);

                try {
                    DFAgentDescription[] results = DFService.search(myAgent, template);

                    if (results.length == 0) {
                        System.out.println("Aucun agent Imprimante trouve dans le DF.");
                        return;
                    }

                    AID imprimante = results[0].getName();

                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.addReceiver(imprimante);

                    if (counter == 1) {
                        msg.setContent("Quitter");
                        send(msg);
                        System.out.println("Message Quitter envoye. Bob se supprime aussi.");
                        stop();
                        doDelete();
                        return;
                    }

                    msg.setContent("Hello, es-tu la ?");
                    counter++;
                    send(msg);
                    System.out.println("Message envoye a " + imprimante.getLocalName() + ". (tick " + counter + ")");

                } catch (FIPAException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}