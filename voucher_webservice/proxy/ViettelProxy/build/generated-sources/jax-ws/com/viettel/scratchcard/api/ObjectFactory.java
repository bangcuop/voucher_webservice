
package com.viettel.scratchcard.api;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.viettel.scratchcard.api package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _QueryCardStatusResponse_QNAME = new QName("http://api.scratchcard.viettel.com/", "queryCardStatusResponse");
    private final static QName _TopupCardResponse_QNAME = new QName("http://api.scratchcard.viettel.com/", "topupCardResponse");
    private final static QName _QueryResultTransaction_QNAME = new QName("http://api.scratchcard.viettel.com/", "queryResultTransaction");
    private final static QName _QueryCardStatus_QNAME = new QName("http://api.scratchcard.viettel.com/", "queryCardStatus");
    private final static QName _QueryResultTransactionResponse_QNAME = new QName("http://api.scratchcard.viettel.com/", "queryResultTransactionResponse");
    private final static QName _TopupCard_QNAME = new QName("http://api.scratchcard.viettel.com/", "topupCard");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.viettel.scratchcard.api
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TopupCard }
     * 
     */
    public TopupCard createTopupCard() {
        return new TopupCard();
    }

    /**
     * Create an instance of {@link QueryResultTransactionResponse }
     * 
     */
    public QueryResultTransactionResponse createQueryResultTransactionResponse() {
        return new QueryResultTransactionResponse();
    }

    /**
     * Create an instance of {@link QueryCardStatus }
     * 
     */
    public QueryCardStatus createQueryCardStatus() {
        return new QueryCardStatus();
    }

    /**
     * Create an instance of {@link TopupCardResponse }
     * 
     */
    public TopupCardResponse createTopupCardResponse() {
        return new TopupCardResponse();
    }

    /**
     * Create an instance of {@link QueryCardStatusResponse }
     * 
     */
    public QueryCardStatusResponse createQueryCardStatusResponse() {
        return new QueryCardStatusResponse();
    }

    /**
     * Create an instance of {@link QueryResultTransaction }
     * 
     */
    public QueryResultTransaction createQueryResultTransaction() {
        return new QueryResultTransaction();
    }

    /**
     * Create an instance of {@link PnResponse }
     * 
     */
    public PnResponse createPnResponse() {
        return new PnResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryCardStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.scratchcard.viettel.com/", name = "queryCardStatusResponse")
    public JAXBElement<QueryCardStatusResponse> createQueryCardStatusResponse(QueryCardStatusResponse value) {
        return new JAXBElement<QueryCardStatusResponse>(_QueryCardStatusResponse_QNAME, QueryCardStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TopupCardResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.scratchcard.viettel.com/", name = "topupCardResponse")
    public JAXBElement<TopupCardResponse> createTopupCardResponse(TopupCardResponse value) {
        return new JAXBElement<TopupCardResponse>(_TopupCardResponse_QNAME, TopupCardResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryResultTransaction }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.scratchcard.viettel.com/", name = "queryResultTransaction")
    public JAXBElement<QueryResultTransaction> createQueryResultTransaction(QueryResultTransaction value) {
        return new JAXBElement<QueryResultTransaction>(_QueryResultTransaction_QNAME, QueryResultTransaction.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryCardStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.scratchcard.viettel.com/", name = "queryCardStatus")
    public JAXBElement<QueryCardStatus> createQueryCardStatus(QueryCardStatus value) {
        return new JAXBElement<QueryCardStatus>(_QueryCardStatus_QNAME, QueryCardStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryResultTransactionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.scratchcard.viettel.com/", name = "queryResultTransactionResponse")
    public JAXBElement<QueryResultTransactionResponse> createQueryResultTransactionResponse(QueryResultTransactionResponse value) {
        return new JAXBElement<QueryResultTransactionResponse>(_QueryResultTransactionResponse_QNAME, QueryResultTransactionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TopupCard }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.scratchcard.viettel.com/", name = "topupCard")
    public JAXBElement<TopupCard> createTopupCard(TopupCard value) {
        return new JAXBElement<TopupCard>(_TopupCard_QNAME, TopupCard.class, null, value);
    }

}
