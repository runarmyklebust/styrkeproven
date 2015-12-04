package no.styrkeproven.htmlmail;

import java.util.Arrays;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

public class MailSender
{

    public static final String TEXT_HTML_CHARSET_UTF_8 = "text/html; charset=\"utf-8\"";

    public static final String UTF_8 = "utf-8";

    public static final String ENCODING = "Q";

    private String smtpHost;

    private String mailTo;

    private String mailFrom;

    private String mailFromName;

    private String mailSubject;

    private String mailMessage;

    private MailSender( Builder builder )
    {
        smtpHost = builder.smtpHost;
        mailTo = builder.mailTo;
        mailFrom = builder.mailFrom;
        mailFromName = builder.mailFromName;
        mailSubject = builder.mailSubject;
        mailMessage = builder.mailMessage;
    }

    public void send()
        throws Exception
    {
        Properties props = new Properties();
        props.put( "mail.smtp.host", this.smtpHost );

        Session session = javax.mail.Session.getDefaultInstance( props );

        //  create message part
        Message message = new MimeMessage( session );

        //  mail from
        InternetAddress addressFrom = new InternetAddress( this.mailFrom, this.mailFromName );
        message.setFrom( addressFrom );

        //  mail to
        InternetAddress addressTo = new InternetAddress( this.mailTo );
        message.setRecipient( Message.RecipientType.TO, addressTo );

        // create multipart of singlepart dependent on if attachment is present
        message.setSubject( MimeUtility.encodeText( this.mailSubject, UTF_8, ENCODING ) );
        message.setContent( this.mailMessage, TEXT_HTML_CHARSET_UTF_8 );

        // Ship ahoy!
        Transport.send( message );
    }

    public static Builder create()
    {
        return new Builder();
    }


    public static final class Builder
    {
        private String smtpHost;

        private String mailTo;

        private String mailFrom;

        private String mailFromName;

        private String mailSubject;

        private String mailMessage;

        private Builder()
        {
        }

        public Builder smtpHost( String smtpHost )
        {
            this.smtpHost = smtpHost;
            return this;
        }

        public Builder mailTo( String mailTo )
        {
            this.mailTo = mailTo;
            return this;
        }

        public Builder mailFrom( String mailFrom )
        {
            this.mailFrom = mailFrom;
            return this;
        }

        public Builder mailFromName( String mailFromName )
        {
            this.mailFromName = mailFromName;
            return this;
        }

        public Builder mailSubject( String mailSubject )
        {
            this.mailSubject = mailSubject;
            return this;
        }

        public Builder mailMessage( String mailMessage )
        {
            this.mailMessage = mailMessage;
            return this;
        }

        public MailSender build()
        {
            return new MailSender( this );
        }
    }
}
