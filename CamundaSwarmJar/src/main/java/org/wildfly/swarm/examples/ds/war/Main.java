package org.wildfly.swarm.examples.ds.war;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

import de.hrw.bpm.app.LoggerDelegate;

/**
 * @author Bob McWhirter
 */
public class Main {

	public static void main(String[] args) throws Exception {
		System.out.println("Running " + Main.class.getCanonicalName() + ".main");

		Container container = new Container();

		// String useDB = System.getProperty("swarm.use.db", "postgres");
		String useDB = "h2";

		// Configure the Datasources subsystem with a driver
		// and a datasource
		switch (useDB.toLowerCase()) {
		case "h2":
			System.out.println("case h2 -> use db " + useDB);
			container.fraction(datasourceWithH2());
			break;
		case "postgresql":
			System.out.println("case postgresql -> use db " + useDB);
			container.fraction(datasourceWithPostgresql());
			break;
		default:
			System.out.println("case default -> use db " + useDB);
			container.fraction(datasourceWithH2());
		}

		// Start the container
		container.start();

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

		// Deploy your app
		container.deploy(deployment);

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

	private static DatasourcesFraction datasourceWithPostgresql() {
		return new DatasourcesFraction().jdbcDriver("org.postgresql", (d) -> {
			d.driverClassName("org.postgresql.Driver");
			d.xaDatasourceClass("org.postgresql.xa.PGXADataSource");
			d.driverModuleName("org.postgresql");
		}).dataSource("ExampleDS", (ds) -> {
			ds.driverName("org.postgresql");
			ds.connectionUrl("jdbc:postgresql://localhost:5432/camunda");
			ds.userName("postgres");
			ds.password("R0ssh!rt");
		});
	}

}
