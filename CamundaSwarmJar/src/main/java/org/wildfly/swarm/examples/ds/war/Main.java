package org.wildfly.swarm.examples.ds.war;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

import de.hrw.bpm.app.LoggerDelegate;

public class Main {

	public static void main(String[] args) throws Exception {
		System.out.println("Running " + Main.class.getCanonicalName() + ".main");

		// Instantiate the swarm
		Swarm swarm = new Swarm();

		// String useDB = System.getProperty("swarm.use.db", "postgres");
		String useDB = "h2";
		
		// // Configure the Datasources subsystem with a driver
		// // and a datasource
		// switch (useDB.toLowerCase()) {
		// case "h2":
		// System.out.println("case h2 -> use db " + useDB);
		// swarm.fraction(datasourceWithH2());
		// break;
		// default:
		// System.out.println("case default -> use db " + useDB);
		// swarm.fraction(datasourceWithH2());
		// }

		JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
		deployment.addResource(MyResource.class);

		deployment.addClasses(LoggerDelegate.class);

		deployment.addAsWebInfResource(new ClassLoaderAsset("META-INF/persistence.xml", Main.class.getClassLoader()),
				"classes/META-INF/persistence.xml");

		deployment.addAsWebInfResource(new ClassLoaderAsset("META-INF/processes.xml", Main.class.getClassLoader()),
				"classes/META-INF/processes.xml");

		deployment.addAsWebInfResource(new ClassLoaderAsset("process.bpmn", Main.class.getClassLoader()),
				"classes/process.bpmn");

		deployment.addAllDependencies();

		// Start swarm
		swarm.start();
		swarm.deploy(deployment);

	}

	private static DatasourcesFraction datasourceWithH2() {
		return new DatasourcesFraction().jdbcDriver("h2", (d) -> {
			d.driverClassName("org.h2.Driver");
			d.xaDatasourceClass("org.h2.jdbcx.JdbcDataSource");
			d.driverModuleName("com.h2database.h2");
		}).dataSource("ExampleDS", (ds) -> {
			ds.driverName("h2");
			ds.connectionUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
			ds.userName("sa");
			ds.password("sa");
		});
	}

}
