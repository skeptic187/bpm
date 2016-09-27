package org.wildfly.swarm.examples.ds.war;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.management.ManagementFraction;
import org.wildfly.swarm.undertow.WARArchive;

import de.hrw.bpm.app.ApproveOrderController;
import de.hrw.bpm.app.OrderBusinessLogic;
import de.hrw.bpm.app.OrderEntity;

public class Main {

	public static void main(String[] args) throws Exception {

		// Instantiate the swarm
		Swarm swarm = new Swarm()
				.fraction(new ManagementFraction().createDefaultFraction().httpInterfaceManagementInterface((iface) -> {
					iface.securityRealm("ManagementRealm");
				}).securityRealm("ManagementRealm", (realm) -> {
					realm.inMemoryAuthentication((authn) -> {
						authn.add("bob", "tacos!", true);
					});
					realm.inMemoryAuthorization((authz) -> {
						authz.add("bob", "admin");
					});
				}));

		// Create deployment
		WARArchive deployment = ShrinkWrap.create(WARArchive.class);

		// Add Classes
		deployment.addClasses(ApproveOrderController.class);
		deployment.addClasses(OrderBusinessLogic.class);
		deployment.addClasses(OrderEntity.class);

		// Add WEB-INF Resources
		deployment.addAsWebInfResource(new ClassLoaderAsset("META-INF/persistence.xml", Main.class.getClassLoader()),
				"classes/META-INF/persistence.xml");

		deployment.addAsWebInfResource(new ClassLoaderAsset("META-INF/processes.xml", Main.class.getClassLoader()),
				"classes/META-INF/processes.xml");

		deployment.addAsWebInfResource(new ClassLoaderAsset("pizza-order.bpmn", Main.class.getClassLoader()),
				"classes/pizza-order.bpmn");

		// Add dependencies defined in pom.xml
		deployment.addAllDependencies();

		// Start swarm
		swarm.start();
		swarm.deploy(deployment);

	}

}
