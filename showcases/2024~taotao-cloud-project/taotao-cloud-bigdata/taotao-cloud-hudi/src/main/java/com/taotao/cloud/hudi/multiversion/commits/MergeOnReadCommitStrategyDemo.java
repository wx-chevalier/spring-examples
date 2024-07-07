package com.taotao.cloud.hudi.multiversion.commits;

import com.taotao.cloud.hudi.common.CustomDataGenerator;
import com.taotao.cloud.hudi.common.OpType;
import com.taotao.cloud.hudi.multiversion.MultiVersionDemo;
import java.util.HashMap;
import java.util.Map;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;

/**
 * KEEP_LATEST_COMMITS Strategy, will retain two version of files.
 */
public class MergeOnReadCommitStrategyDemo extends CommitStrategyMultiVersion {
    private static String basePath = "/tmp/multiversion/commits/mergeonread/";

    public MergeOnReadCommitStrategyDemo(Map<String, String> properties) {
        super(properties, basePath);
    }

    public static void main(String[] args) {
        Map<String, String> config = new HashMap<>();
        // config to keep there is 2 commit instants, so that the clean action make sense.
        config.put("hoodie.keep.max.commits", "4");
        config.put("hoodie.keep.min.commits", "3");
        config.put("hoodie.cleaner.commits.retained", "1");
        // enable inline compaction, true value is false
        config.put("hoodie.compact.inline", "true");
        // NOTE that when "hoodie.compact.inline.max.delta.commits" is less than hoodie.keep.max.commits,
        // there will be no compaction since the delta active instant is less than "hoodie.compact.inline.max.delta.commits"
        config.put("hoodie.compact.inline.max.delta.commits", "1");
        MultiVersionDemo morMultiVersionDemo = new MergeOnReadCommitStrategyDemo(config);

        Dataset<Row> dataset = CustomDataGenerator.getCustomDataset(10, OpType.INSERT, spark);

        morMultiVersionDemo.writeHudi(dataset, SaveMode.Overwrite);

        for (int i = 0; i < 10; i++) {
            dataset = CustomDataGenerator.getCustomDataset(10, OpType.UPDATE, i, "shanghai", spark);
            morMultiVersionDemo.writeHudi(dataset, SaveMode.Append);
        }
    }

    @Override
    public String tableType() {
        return "MERGE_ON_READ";
    }
}
