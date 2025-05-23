package com.saninco.pdtstructuredoutput.service;

import com.saninco.pdtstructuredoutput.ai.OpenAiClient;
import com.saninco.pdtstructuredoutput.model.BillParseResult;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BillParseService {
    @Autowired
    private OpenAiClient openAiClient;
    @Autowired
    private OpenAiChatModel chatModel;


    public BillParseResult parseBill(String rawText) {

        BeanOutputConverter<BillParseResult> converter = new BeanOutputConverter<>(BillParseResult.class);

        rawText =
                "PAGE 1 OF 2\n" +
                        "D700B Account #: 4607651\n" +
                        "Service Address: 21 ACCPAC ST, REGINA Invoice Date: May 1, 2025\n" +
                        "Billing Period: Jun 1, 2025 to Jun 30, 2025 Billing Frequency: MONTHLY\n" +
                        "DUE DATE MAY 27, 2025\n" +
                        " \n" +
                        "Please return this portion with your payment \n" +
                        "Customer Name: D700B\n" +
                        "Account # 4607651\n" +
                        " \n" +
                        "D700B\n" +
                        "ROGERS COMMUNICATIONS CANADA INC\n" +
                        "DUE DATE MAY 27, 2025\n" +
                        "AMOUNT DUE $7980.00\n" +
                        "AMOUNT ENCLOSED $\n" +
                        "GST #R104452073\n" +
                        "      a05042d900a                96\n" +
                        "BILLING INQUIRIES\n" +
                        "Regina 306.569.2225\n" +
                        "or Provincial 1.866.363.2225\n" +
                        "Visit us online at www.myaccess.ca\n" +
                        "Please note that your call will be recorded for quality assurance\n" +
                        "purposes.\n" +
                        "Receive a one-time $10 credit on your\n" +
                        "account by switching to eBill, our paperless\n" +
                        "billing service.  When you make the switch\n" +
                        "enjoy instant access to your account,\n" +
                        "delivery of your bill right to your email, full\n" +
                        "control from setting up pre-authorized\n" +
                        "payments to making one-time payments,\n" +
                        "plus view your account history and more.\n" +
                        "Transactions are safe and secure. Join us in\n" +
                        "making a difference for the environment.\n" +
                        "Visit https://account.myaccess.ca/ to switch\n" +
                        "today or please call our Customer Care team\n" +
                        "at 1-866-363-2225.\n" +
                        "ACCOUNT SUMMARY\n" +
                        "Balance from Previous Statement  20216.00\n" +
                        "Payment Received April 21, 2025 -2216.00\n" +
                        "Payment Received April 21, 2025 -9000.00\n" +
                        "Payment Received April 21, 2025 -9000.00\n" +
                        " $0.00\n" +
                        " \n" +
                        "CURRENT CHARGES (Details on following pages)\n" +
                        "Internet Services  7600.00\n" +
                        "Taxes and Fees  380.00\n" +
                        "TOTAL CURRENT CHARGES  $7980.00\n" +
                        "TOTAL AMOUNT DUEDue May 27, 2025 $7980.00\n" +
                        "  PAGE 2 OF 2\n" +
                        "PAYMENT OPTIONS\n" +
                        "Your bill can be paid by:\n" +
                        "• pre-authorized cheque or pre-authorized credit card;\n" +
                        "• Internet or telephone banking;\n" +
                        "• mail to Access Communications 2250 Park St, Regina SK S4N 7K7;\n" +
                        "• at most chartered banks, trust companies or credit unions;\n" +
                        "• at our offices by cash, cheque, debit card, Visa, Mastercard.\n" +
                        "OVERDUE ACCOUNTS\n" +
                        "Any balance unpaid after the due date may be subject to a late payment\n" +
                        "charge of 2% per month (26.82% per annum). This rate may be revised\n" +
                        "by Access Communications at any time upon 30 days notice. An\n" +
                        "administration fee will be levied if your credit card or pre-authorized\n" +
                        "payment is denied or if your cheque is returned. A fee will be charged if\n" +
                        "administration and/or account processing activities have occurred due to\n" +
                        "non-payment.\n" +
                        "Do you have a complaint regarding a telecom or residential TV service that we haven't been able to resolve? The independent Commission for\n" +
                        "Complaints for Telecom-television Services (CCTS) may be able to assist you free of charge: www.ccts-cprst.ca or 1-888-221-1687.\n" +
                        "You can review the most current version of our Terms of Service at www.myaccess.ca or by requesting a copy from Customer Service.\n" +
                        "Email: customer.care@myaccess.coop\n" +
                        "CURRENT CHARGE DETAILS\n" +
                        "INTERNET SERVICES SERVICE PERIOD QTY AMOUNT \n" +
                        "FIBRE - 10G WAVE P2P ACC/E1G/4607651/000A - OLD ACCT\n" +
                        "3024, 370 HOFFER DR REGINA\n" +
                        "June 1 to June 30, 2025  3800.00 \n" +
                        "FIBRE - 10G WAVE P2P ACC/E1G/4607651/000B - OLD ACCT 3024,\n" +
                        "370 HOFFER DR REGINA\n" +
                        "June 1 to June 30, 2025  3800.00 \n" +
                        "TOTAL FOR INTERNET SERVICES $7600.00  \n" +
                        " \n" +
                        "TAXES AND FEES  AMOUNT \n" +
                        "PST Registration # 0999334  0.00 \n" +
                        "GST Registration # R104452073  380.00 \n" +
                        "TOTAL FOR TAXES AND FEES $380.00  \n" +
                        " \n" +
                        " \n" +
                        "TOTAL CURRENT CHARGES $7980.00 \n";


        String template = """
            请阅读下方账单原文，抽取并输出结构化账单信息。你的输出必须是严格的JSON格式（不要有多余的解释），字段说明如下：
            
            1. **barcode**: 二维码或账单条码（如无可为空）
            2. **paperAccountNumber**: 账单上的纸质账号（如无可为空）
            3. **accountNumber**: 账单账号
            4. **originalInvoiceNumber**: 原始发票编号（如有）
            5. **invoiceDate**: 账单日期，格式为 yyyy-MM-dd
            6. **invoiceDueDate**: 到期日，格式为 yyyy-MM-dd
            7. **invoiceStartDate**: 账期开始日期，格式为 v
            8. **invoiceEndDate**: 账期结束日期，格式为 yyyy-MM-dd
            9. **invoiceCurrentDUE**: 当前应付金额
            10. **invoiceTotalDUE**: 账单总金额
            11. **mrcAmount**: 月租金额
            12. **occAmount**: 一次性费用
            13. **usageAmount**: 使用费
            14. **lpcAmount**: 滞纳金
            15. **taxAmount**: 税金
            16. **adjustmentAmount**: 调整金额
            17. **invoicePreviousBalance**: 上期结余
            18. **invoicePreviousAdjustment**: 上期调整
            19. **invoicePreviousPayment**: 上期已付
            20. **invoiceBalanceForward**: 结转余额
            21. **invoiceItems**: 账单明细（列表，每项结构如下）：
            
            **每个 invoiceItems 元素为对象，字段如下：**
            - **itemBarCode**: 明细条码（如有）
            - **itemTypeId**: 项目类型编码 (PREVIOUS_PAYMENT = "11", LPC = "12", MRC = "13", USAGE = "14", OCC = "15", ADJUSTMENT = "16", CREDIT = "17", TAX = "18", PREVIOUS_ADJUSTMENT = "19")
            - **itemName**: 项目名称/服务名称
            - **itemAmount**: 明细金额
            - **rate**: 费率
            - **quantity**: 数量
            - **startDate**: 服务起始日期，yyyy-MM-dd
            - **endDate**: 服务结束日期，yyyy-MM-dd
            - **taxCodeId**: 税种编码
            - **taxNumber**: 税号
            - **description**: 详细描述
            - **circuitNumber**: 电路编号（如有）
            - **itemDate**: 项目日期
            - **location**: 服务地址
            - **chargeType**: 计费类型
            
            账单原文如下：
            {billText}
            
            {format}
            """;


        Generation generation = chatModel.call(
                new PromptTemplate(template, Map.of(
                        "billText", rawText,
                        "format", converter.getFormat()
                )).create()).getResult();

        BillParseResult billResult = converter.convert(generation.getOutput().getText());
        // 反序列化成 BillParseResult
        return billResult;
    }


//    // 组装 prompt，描述你希望 AI 做什么
//    String prompt = rawText;
//
//    // 调用 AI，拿到结构化结果
//    String aiResponse = openAiClient.askAiForStructuredOutput(prompt);
//
//    // 反序列化成 BillParseResult
//        return aiResponse;
}
