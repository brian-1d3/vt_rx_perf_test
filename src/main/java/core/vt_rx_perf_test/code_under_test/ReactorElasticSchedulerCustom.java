package core.vt_rx_perf_test.code_under_test;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ReactorElasticSchedulerCustom implements AsyncTaskHandler {
	@Override // Schedulers.DEFAULT_BOUNDED_ELASTIC_SIZE * 10000 -> seems to make absolutly no difference!
	public void doAsync( Runnable runnable ) {
		Mono.fromRunnable( runnable )
				.publishOn( Schedulers.newBoundedElastic( Schedulers.DEFAULT_BOUNDED_ELASTIC_SIZE * 10000,
						Integer.MAX_VALUE,
						"ReactorElasticSchedulerCustom" ) )
				.subscribe();
	}
}
