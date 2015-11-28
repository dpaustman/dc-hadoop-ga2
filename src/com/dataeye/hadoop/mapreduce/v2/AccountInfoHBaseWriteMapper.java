package com.dataeye.hadoop.mapreduce.v2;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.dataeye.hadoop.common.dewritable.DEDynamicKV;
import com.dataeye.hadoop.domain.common.MRConstants;
import com.dataeye.hadoop.domain.kv.AccountRollInfo;
import com.dataeye.hadoop.util.AccountCacheUtil;
import com.dataeye.hadoop.util.DCDateUtil;
import com.dataeye.hadoop.util.HBaseUtil;

public class AccountInfoHBaseWriteMapper extends Mapper<LongWritable, Text, DEDynamicKV, DEDynamicKV> {

	private int dataDate = 0;

	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		dataDate = DCDateUtil.getDataDate(context.getConfiguration());
		HBaseUtil.init(context.getConfiguration());
	}

	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		AccountRollInfo info = new AccountRollInfo(value.toString().split(MRConstants.FIELDS_SEPARATOR));
		updateAccountDayInfo(info, context);
		updateAccountHistoryInfo(info, context);
	}

	private void updateAccountDayInfo(AccountRollInfo rollInfo, Context context) throws IOException,
			InterruptedException {

	}

	private void updateAccountHistoryInfo(AccountRollInfo rollInfo, Context context) throws IOException,
			InterruptedException {
		AccountCacheUtil.updateAccountHistoryInfo(rollInfo, dataDate);
	}

	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {
	}
}
